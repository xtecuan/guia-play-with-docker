#!/bin/bash

export REPOSITORY_URL="https://github.com/xtecuan/guia-play-with-docker.git"

apk add nano zip unzip curl

mkdir -p $HOME/Projects/httpd
cd $HOME/Projects
git clone $REPOSITORY_URL
ln -s $HOME/Projects/guia-play-with-docker  $HOME/Projects/guia1
cp $HOME/Projects/guia1/httpd/index.html $HOME/Projects/httpd/
