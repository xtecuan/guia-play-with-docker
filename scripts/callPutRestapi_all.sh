#!/bin/bash


export APP=$(dirname $(realpath $0))
source ${APP}/env.sh

export books='book1patch book2patch book3patch'

for i in ${books}
do
    ${APP}/callPutRestapi.sh ${i}
done