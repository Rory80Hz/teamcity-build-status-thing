## Build Status Service
Has stuff in it for checking team city builds, this is specific right now to the project im working on, and generally approximates what teams broke things.

### SRSLY?
Two parts, one is a Dropwizard service that connects to team city to parse it's labyrinthine API to get broken builds.

The other is a terrible front end that is built in angular, and runs in node.

Reason for this is, that someone way more competent than me, can build a nicer UI for this.

### Make it so....

#### Dropwizard service
So this won't compile, because i hardcoded my basic auth connection to team city...because i am a terrible person. So fix that first...then....in the root folder just run the go shell script.


#### Front End
Go to the front-end folder and type

```
npm start
```

Then browse to the port it runs on on your local machine, then wait ages.
