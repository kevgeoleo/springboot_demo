# React / Springboot demo project

# Requirements

- Java 21
- Node.js
- npm

## Steps to run

1. Step 1:

```
cd frontend
npm install
npm run dev
```

2. Step 2:

```
cd backend
.\mvnw.cmd spring-boot:run
```

3. Step 3:
   Visit `localhost:5173`

## NOTE
Instead of storing 50 outgoing transfers, I stored all types of transactions (WITHDRAW, DEPOSIT, TRANSFER) - please excuse that

## TODO

1. Dialog boxes containing input boxes instead of directly showing input boxes - Create account, Withdraw, Deposit, Transfer, Login
2. No need for FROM and TO in Withdrawal and Deposit
3. Print transaction history in reverse order (NEWEST first)

