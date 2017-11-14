#!/usr/bin/env bash

gcloud compute ssh edge-netflix --command "./run.sh $1"
