# EV Charging Station Slot Booking and Billing System

Java 21 project for the CSA09 Programming in Java assessment.

## Fastest run
1. Open this folder in VS Code.
2. Make sure JDK 21 is installed.
3. Run `ev.app.EVChargingApp`.
4. The console first demonstrates multithreading.
5. A Swing GUI then opens for slot booking and billing.

## Compile
Windows PowerShell:
`javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object {$_.FullName})`

Linux/macOS:
`javac -d bin $(find src -name "*.java")`

## Run
`java -cp bin ev.app.EVChargingApp`

## JDBC
The DAO/database layer is included. For database execution, install MySQL and add MySQL Connector/J to the project classpath, then run `sql/ev_charging_db.sql`.
