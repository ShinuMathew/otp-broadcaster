# OTP BROADCASTER
`A simple android app to broadcast the otp received on a device to a remote server using a webhook api`

* OTP from the message is received, read and scrapped
* The OTP Broadcaster then sends the OTP to a remote server(heroku for this project) using a webhook api
* The webhook stored the otp on a remote mongo server(atlas for this project) with a "PENDING" process tag
* The consumer calls the otp broadcaster service and gets the latest otp

[ANDROID MATERIAL DESIGN](https://material.io/design)