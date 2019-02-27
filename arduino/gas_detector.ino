#include <SoftwareSerial.h>

// Initializing communication ports
SoftwareSerial mySerial(11, 10); // TX/RX pins

void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);
}

void loop() {
  mySerial.print("NO2=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("SO2=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("O2=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("Cl=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("Ar=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("Xe=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("H2=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("He=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("N2=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("F=");
  mySerial.print(random(100,900));
  mySerial.print(",");
  mySerial.print("Ne=");
  mySerial.print(random(100,900));
  mySerial.print(";");
  delay(2000);
}
