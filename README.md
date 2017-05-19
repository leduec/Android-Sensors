# Android-Sensors

## Abstract

- simple user interface (GUI, Activity)
- collect data from the phone's sensors
- store data on the SD-card

## Functions

When the app is started it shows an Activity that displays the GPS location (longitude/latitude/height), the three values from accelerometer, the magnetometer, the proximity sensor, the brightness sensor and the gyroscope.

A second Activity enables to select the data that is recorded and the file where the data is stored. Chechboxes are used to (de-)select the sensors and a text field to change the filename.

The third Activity is used to start and stop the data recording. Two big buttons are used, one for starting the recording and one to stop it. The button that is currently not useful is disabled.

Uses a menu to switch between the three activities.

The data is stored on the phone's SD-card using the file name entered by the user. A plain text file is used where each line contain a timestamp and the values of all sensors separated by tabs.
