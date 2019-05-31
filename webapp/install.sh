#!/bin/bash
if ! hash brew 2>/dev/null; then 
  brew install yarn
  brew install npm
fi


if ! hash choco 2>/dev/null; then
  choco install yarn
  choco install npm
fi

if hash yarn 2>/dev/null; then
  yarn install
else
  echo 'install yarn manually from https://yarnpkg.com/en/docs/install#windows-stable'
fi

if hash npm 2>/dev/null; then
  npm install -g @angular/cli
else
  echo 'install https://nodejs.org/en/download/'
fi
