#!/bin/bash

cd

mv projects Projects

cd $HOME/Projects
rm guia1

ln -s $HOME/Projects/guia-play-with-docker $HOME/Projects/guia1

echo "Su ambiente fue parchado exitosamente by Xtecuan"

