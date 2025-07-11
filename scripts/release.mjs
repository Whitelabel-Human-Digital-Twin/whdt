import { execSync } from 'child_process';
import { readFileSync, writeFileSync } from 'fs';
import { basename } from 'path';

function getModules() {
  const settings = readFileSync('settings.gradle.kts', 'utf-8');
  const includeLine = settings.match(/include\(([^)]+)\)/);
  if (!includeLine) return [];

  return includeLine[1]
    .split(',')
    .map(s => s.trim().replace(/["']/g, '')); // strip quotes
}

function run(cmd, opts = {}) {
  return execSync(cmd, { stdio: 'inherit', ...opts });
}

const modules = getModules();
const changedFiles = execSync('git diff --name-only HEAD~1 HEAD').toString().split('\n');
const changedModules = modules.filter(mod => changedFiles.some(file => file.startsWith(`${mod}/`)));

if (changedModules.length === 0) {
  console.log('✅ No modules changed — skipping release.');
  process.exit(0);
}

for (const module of changedModules) {
  const versionPath = `${module}/version.txt`;
  const oldVersion = readFileSync(versionPath, 'utf-8').trim();
  const [major, minor, patch] = oldVersion.split('.').map(Number);
  const newVersion = `${major}.${minor}.${patch + 1}`;
  const branch = `release/${module}-v${newVersion}`;

  console.log(`\n🚀 Releasing ${module}: ${oldVersion} → ${newVersion}`);
  console.log(`🌿 Creating branch: ${branch}`);

  // Checkout new release branch
  run(`git checkout -b ${branch}`);

  // Update version
  writeFileSync(versionPath, newVersion);

  // Commit
  run(`git config user.name "github-actions"`);
  run(`git config user.email "github-actions@github.com"`);
  run(`git add ${versionPath}`);
  run(`git commit -m "chore(${module}): bump to v${newVersion}"`);

  // Push branch
  run(`git push origin ${branch}`);

  // Open PR via GitHub CLI
  const prTitle = `chore(${module}): release v${newVersion}`;
  const prBody = `Auto-generated PR to release \`${module}\` version \`${newVersion}\``;

  try {
    run(`gh pr create --base main --head ${branch} --title "${prTitle}" --body "${prBody}"`);
  } catch (e) {
    console.error(`❌ Failed to open PR for ${module}:`, e.message);
  }
}
