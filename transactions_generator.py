import random
from datetime import datetime, timedelta

categories = ["groceries", "electronics", "books"]

random.seed(100)

last_transaction_dt = datetime(2025, 1, 1)

with open("transactions.csv", "w", encoding="utf-8", newline="\n") as f:
    f.write("user_id,timestamp,category,amount\n")
    for i in range(1000000):
        user_id = random.randint(1000,10000)
        ts = last_transaction_dt + timedelta(seconds=random.randint(10, 100))
        category = random.choices(categories, weights=[20, 15, 10], k=1)[0]
        amount = random.uniform(3.0, 120.0)

        f.write(f"{user_id},{ts.isoformat()},{category},{amount:.2f}\n")

        last_transaction_dt = ts