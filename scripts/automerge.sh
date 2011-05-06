#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 their_git_url their_branch our_git_url our_branch"
    exit;
fi

THEIR_GIT_URL=$1
THEIR_BRANCH=$2
OUR_GIT_URL=$3
OUR_BRANCH=$4

THEIR_REMOTE_NAME="theirRepo"
OUR_REMOTE_NAME="ourRepo"

fail() {
  #git remote rm $THEIR_REMOTE_NAME
  git reset --hard HEAD
  exit 1;
}

git remote add $THEIR_REMOTE_NAME $THEIR_GIT_URL
git remote add $OUR_REMOTE_NAME $OUR_GIT_URL
git fetch $THEIR_REMOTE_NAME
git fetch $OUR_REMOTE_NAME

echo "Git remotes:"
git remote -v -v
echo "Git branches:"
git branch -v -v
echo

git checkout $OUR_REMOTE_NAME/$OUR_BRANCH
git merge $THEIR_REMOTE_NAME/$THEIR_BRANCH || { echo "Automatic merge failed. Resetting to HEAD."; fail; }

#git remote rm $THEIR_REMOTE_NAME
#git remote rm $OUR_REMOTE_NAME
