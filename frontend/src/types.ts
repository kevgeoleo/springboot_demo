// Similar types as models in backend

export type Transaction = {
    from: string;
    to: string;
    amount: number;
    originalBalance: number;
    newBalance: number;
    transactionType: string;
    timestamp: string;
};

export type Account = {
    id: number;
    username: string;
    balance: number;
    transactionHistory: Transaction[];
};