#define FORWARD 1
#define BACKWARD 2
#define BRAKE 3
#define RELEASE 4

void setup() {
  Serial.begin(9600);
}

boolean newline = false;
String inputString = "";


void loop()
{

  if(newline){

int i = inputString.toInt();
  i = constrain(i, -250, 250);

  if(i == 0) return;

  if(i < 0){
    motor(4, BACKWARD, -1 * i);
  } else {
    motor(4, FORWARD, i);
  }
  

  Serial.println(i);
  
    newline = false;
    inputString = "";
  }

  
   
}

void serialEvent() {
  while (Serial.available()) {
    char rec = (char)Serial.read();
    inputString += rec;
    if (rec == '\n') {
      newline = true;
    }
  }
}

/*
===========================
Motor Controller Code Below
===========================
*/

void motor(int nMotor, int command, int speed)
{
  int motorA, motorB;

  if (nMotor >= 1 && nMotor <= 4)
  {  
    switch (nMotor)
    {
    case 1:
      motorA   = 2;
      motorB   = 3;
      break;
    case 4:
      motorA   = 0;
      motorB   = 6;
      break;
    default:
      break;
    }

    switch (command)
    {
    case FORWARD:
      motor_output (motorA, HIGH, speed);
      motor_output (motorB, LOW, -1);
      break;
    case BACKWARD:
      motor_output (motorA, LOW, speed);
      motor_output (motorB, HIGH, -1);
      break;
    case BRAKE:
      motor_output (motorA, LOW, 255);
      motor_output (motorB, LOW, -1);
      break;
    case RELEASE:
      motor_output (motorA, LOW, 0);
      motor_output (motorB, LOW, -1);
      break;
    default:
      break;
    }
  }
}

void motor_output (int output, int high_low, int speed)
{
  int motorPWM;

  switch (output)
  {
  case 2:
  case 3:
    motorPWM = 11;
    break;
  case 0:
  case 6:
    motorPWM = 5;
    break;
  default:
    speed = -3333;
    break;
  }

  if (speed != -3333)
  {
    shiftWrite(output, high_low);

    if (speed >= 0 && speed <= 255)    
    {
      analogWrite(motorPWM, speed);
    }
  }
}
void shiftWrite(int output, int high_low)
{
  static int latch_copy;
  static int shift_register_initialized = false;

  if (!shift_register_initialized)
  {
    pinMode(12, OUTPUT);
    pinMode(7, OUTPUT);
    pinMode(8, OUTPUT);
    pinMode(4, OUTPUT);

    digitalWrite(8, LOW);
    digitalWrite(12, LOW);
    digitalWrite(4, LOW);
    digitalWrite(7, LOW);

    latch_copy = 0;

    shift_register_initialized = true;
  }

  bitWrite(latch_copy, output, high_low);
  shiftOut(8, 4, MSBFIRST, latch_copy);
  delayMicroseconds(5);
  digitalWrite(12, HIGH);
  delayMicroseconds(5);
  digitalWrite(12, LOW);
}
