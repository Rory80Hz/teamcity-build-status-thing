## Build Status Service
Has stuff in it for checking team city builds, this is specific right now to the project im working on, and generally approximates what teams broke things.

### SRSLY?
Two parts, one is a Dropwizard service that connects to team city to parse it's labyrinthine API to get broken builds.

The other is a terrible front end that is built in angular, and runs in node.

Reason for this is, that someone way more competent than me, can build a nicer UI for this.

### Make it so....

#### Dropwizard service
This now compiles...NO WAI!

But...to run it locally create a copy of the .yml file and call it myBuildstatusServiceConfiguration.yml in /configuration/local/services make sure you get your nice base64 encoded UserName:Password and the team city base URL.

then run

```
./go
```

#### Front End
Go to the front-end folder and type

```
npm start
```

Then browse to the port it runs on on your local machine, then wait ages.
