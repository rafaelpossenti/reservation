import requests
import json
from multiprocessing import Pool
from datetime import date, timedelta

class Reservation:
  def __init__(self, email, name):
    self.email = email
    self.name = name
    self.arrivalDate = str(date.today() + timedelta(days=1))
    self.departureDate = str(date.today() + timedelta(days=3))

  def to_dict(self):
    return {
        "email": self.email,
        "name": self.name,
        "arrivalDate": self.arrivalDate,
        "departureDate": self.departureDate
    }

def executeRequest(*args):
    response = requests.post(url, data=body, headers=headers)
    print(response.text)

reservation = Reservation("gandalf@gmail.com","gandalf")
url="http://localhost:8080/reservations"
body=json.dumps(reservation.to_dict())
headers={"Content-Type": "application/json"}

with Pool(3) as p:
    p.map(executeRequest, range(3))

