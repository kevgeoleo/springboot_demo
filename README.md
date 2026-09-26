# React / Springboot demo project

Author: Kevin George Leo

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

## FRONTEND

1. Landing Page
   - Create user
   - Login
2. User Page
   - Show balance
   - Withdraw
   - Deposit
   - Transfer
   - Show transfer history

## TODO

1. Dialog boxes containing input boxes instead of directly showing input boxes - Create account, Withdraw, Deposit, Transfer, Login
2. Print transaction history in reverse order (NEWEST first)

## Improvements made

1. Account id lookup is expensive (O(n)) due to the use of for loop to iterate List - can be made faster using Concurrent Hash Map
2. Improved for concurrency - for withdraw, deposit and transfer - if 2 transactions take place at same time, can lead to race condition - fixed using synchronize
3. Id increment operation made Atomic
4. Stores only outgoing transfers
5. Account deletion
