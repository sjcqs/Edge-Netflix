#!/usr/bin/env bash

gcloud compute ssh seeder-factory --command "./run.sh $1"
