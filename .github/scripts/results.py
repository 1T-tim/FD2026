import json, os, sys
from pathlib import Path
import xml.etree.ElementTree as ET

suite = sys.argv[1]
xml = Path(f'lesson4/seminar/build/test-results/test/TEST-seminar4.{suite}.xml')
hints = json.loads(Path(__file__).with_name('hints.json').read_text())
lines = [f'### {suite}', '']
cases = ET.parse(xml).getroot().findall('testcase') if xml.exists() else []
success = bool(cases)
if not cases:
    lines.append('Нет результатов тестов. Открой шаг «Сборка и запуск»: проверь сообщения компилятора или Gradle.')
for case in cases:
    key = case.get('name', '').removesuffix('()')
    title, hint = hints.get(key, (key, None))
    failed = any(case.find(t) is not None for t in ('failure', 'error'))
    skipped = case.find('skipped') is not None
    status = 'FAIL' if failed else 'SKIP' if skipped else 'OK'
    success = success and status == 'OK'
    lines.append(f'- **{status}** · {title}')
    if failed and hint:
        lines.append(f'  - Разбери свою программу на примере: `{hint}`')
print('\n'.join(line.replace('**', '').removeprefix('### ') for line in lines))
if 'GITHUB_STEP_SUMMARY' in os.environ:
    with open(os.environ['GITHUB_STEP_SUMMARY'], 'a') as summary:
        print('\n'.join(lines), file=summary)
sys.exit(0 if success else 1)
