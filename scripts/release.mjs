import { execSync } from 'child_process';
import { readFileSync, writeFileSync } from 'fs';

const modules = ['core', 'plugin'];
const changedModules = [];

for (const module of modules) {
  const output = execSync(`git diff --name-only HEAD~1 HEAD`).toString();
  if (output.includes(`${module}/`)) {
    changedModules.push(module);
  }
}

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

  // Trigger Gradle publish for just that module
  execSync(`./gradlew :${module}:publish -PpackageVersion=${newVersion}`, {
    stdio: 'inherit',
    env: {
      ...process.env,
      GPR_USER: process.env.GPR_USER,
      GPR_TOKEN: process.env.GPR_TOKEN,
    },
  });
}