apt install nodejs npm
npm -g install yuidocjs
npm install yuidoc-ember-cli-theme
yuidoc -c yuidoc.json -o docs/api -e .java *
