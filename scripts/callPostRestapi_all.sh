#!/bin/bash


export APP=$(dirname $(realpath $0))
source ${APP}/env.sh

export books='book1 book2 book3'

for i in ${books}
do
    ${APP}/callPostRestapi.sh ${i}
done