import { execSync } from 'child_process';
import { readFileSync, writeFileSync } from 'fs';

function getModules() {
  const settings = readFileSync('settings.gradle.kts', 'utf-8');
  const includeLine = settings.match(/include\(([^)]+)\)/);
  if (!includeLine) return [];

  return includeLine[1]
    .split(',')
    .map(s => s.trim().replace(/["']/g, '')); // strip quotes
}

const modules = getModules();
const changedFiles = execSync('git diff --name-only HEAD~1 HEAD').toString().split('\n');
const changedModules = modules.filter(mod => changedFiles.some(file => file.startsWith(`${mod}/`)));

if (changedModules.length === 0) {
  console.log('No modules changed — skipping release.');
  process.exit(0);
}

for (const module of changedModules) {
  const versionPath = `${module}/version.txt`;
  const oldVersion = readFileSync(versionPath, 'utf-8').trim();
  const [major, minor, patch] = oldVersion.split('.').map(Number);
  const newVersion = `${major}.${minor}.${patch + 1}`;

  console.log(`🔁 ${module}: ${oldVersion} → ${newVersion}`);

  writeFileSync(versionPath, newVersion);
  execSync(`git config user.name "github-actions"`);
  execSync(`git config user.email "github-actions@github.com"`);
  execSync(`git add ${versionPath}`);
  execSync(`git commit -m "chore(${module}): bump to v${newVersion} [skip ci]"`);
  execSync(`git tag ${module}-v${newVersion}`);
  execSync(`git push origin HEAD`);
  execSync(`git push origin ${module}-v${newVersion}`);

  execSync(`./gradlew :${module}:publish -PpackageVersion=${newVersion}`, {
    stdio: 'inherit',
    env: {
      ...process.env,
      GPR_USER: process.env.GPR_USER,
      GPR_TOKEN: process.env.GPR_TOKEN,
    },
  });
}
