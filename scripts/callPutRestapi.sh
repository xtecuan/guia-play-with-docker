#!/bin/bash

export APP=$(dirname $(realpath $0))
source ${APP}/env.sh

export book1patch='{"title":"This is my test book","description":"this is my book description","isbn": "12xxxxxxxx", "publisher": "New Updated", "language":"English","author":"Hayri Cicek","price": "55.00","pages":"100"}'
export book2patch='{"title":"This is my second test book updated","description":"this is my second book description updated","isbn": "13xxxxxxxx", "publisher": "None Yet", "language":"English","author":"Hayri Cicek","price": "1.00","pages":"0"}'
export book3patch='{"title":"Xtecuan Book","description":"This is the book of Tadeo","isbn": "14xxxxxxxx", "publisher": "Grupo Tecuan", "language":"Spanish","author":"Tadeo Rivera-Pineda","price": "10.00","pages":"50"}'

export currentId=2
export currentBook=${book2patch}

if [ -n "$1" ]
then
  case $1 in
     book1patch)
          currentId=1
          currentBook=${book1patch}
          ;;
     book2patch)
          currentId=2
          currentBook=${book2patch}
          ;;
     book3patch)
          currentId=3
          currentBook=${book3patch}
          ;; 
     *)
          currentId=2
          currentBook=${book2patch}
          ;;
  esac
fi

echo "Calling ${myUrl}"

echo "Payload ${currentBook} entity Id: ${currentId}"



curl -H 'Content-Type: application/json' -X PUT \
-d "${currentBook}" ${myUrl}/${currentId}

