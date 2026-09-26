
import type { Account } from "./types";
import { useState } from "react";

// Account will never be null in User Page 
type UserPageProps = {
    account: Account;
    setCurrentAccount: React.Dispatch<
        React.SetStateAction<Account | null>
    >;
};

function UserPage({account, setCurrentAccount}: UserPageProps) {

    const [depositAmount,setDepositAmount] = useState("");
    const [withdrawAmount,setWithdrawAmount] = useState("");
    const [transferTo,setTransferTo] = useState("");
    const [transferAmount,setTransferAmount] = useState("");

    // Sign out
    function signOut() {
        localStorage.removeItem("userId");
        setCurrentAccount(null);
    }

    async function Deposit() {

        // validity check
        if (depositAmount === "" || Number(depositAmount) <= 0 || Number.isNaN(Number(depositAmount))) {
            alert("Invalid amount");
            setDepositAmount("");
            return;
        }

        const response = await fetch(
            `http://localhost:3005/api/${account.id}/deposit?amount=${depositAmount}`,
            {
                method: "PUT"
            }
        );

        const result = await response.json();

        if (result === true) {

            alert("Success");

            // Get updated account - to update transactionHistory
            const updatedResponse = await fetch(
                `http://localhost:3005/api/login/${account.id}`
            );

            const updatedAccount: Account = await updatedResponse.json();

            // Update React state - to update transactionHistory
            setCurrentAccount(updatedAccount);
            setDepositAmount("");
        } else {
            alert("Invalid amount");
        }
    }

    async function deleteAccount(){
        const response = await fetch(
            `http://localhost:3005/api/${account.id}/remove`,
            {
                method: "DELETE"
            }
        );

        const isDeleted = await response.json();

        if (!isDeleted) {
            alert("incorrect account details");
            return;
        }

        // Logout
        setCurrentAccount(null);
    }

    async function Withdraw(){
        
        // validity check
        if (withdrawAmount === "" || Number(withdrawAmount) <= 0 || Number.isNaN(Number(withdrawAmount))) {
            alert("Invalid amount");
            setWithdrawAmount("");
            return;
        }else if(account.balance < Number(withdrawAmount)){
            alert("Insufficient funds");
            setWithdrawAmount("");
            return;
        }

        const response = await fetch(
            `http://localhost:3005/api/${account.id}/withdraw?amount=${withdrawAmount}`,
            {
                method: "PUT"
            }
        );

        const result = await response.json();

        if (result === true) {

            alert("Success");

            // Get updated account - to update transactionHistory
            const updatedResponse = await fetch(
                `http://localhost:3005/api/login/${account.id}`
            );

            const updatedAccount: Account = await updatedResponse.json();

            // Update React state - to update transactionHistory
            setCurrentAccount(updatedAccount);
            setWithdrawAmount("");
        } 
    }

    async function Transfer() {

        // validity checks
        if (transferAmount === "" || Number(transferAmount) <= 0 || Number.isNaN(Number(transferAmount))) {
            alert("Invalid amount");
            setTransferAmount("");
            return;
        }

        if (transferTo === "" || Number.isNaN(Number(transferTo))) {
            alert("Enter a valid receiver ID");
            return;
        }

        if (Number(transferTo) === account.id) {
            alert("Cannot transfer to yourself");
            setTransferTo("");
            return;
        }

        if (account.balance < Number(transferAmount)) {
            alert("Insufficient funds");
            setTransferAmount("");
            return;
        }

        const response = await fetch(
            `http://localhost:3005/api/transfer?fromId=${account.id}&toId=${transferTo}&amount=${transferAmount}`,
            {
                method: "PUT"
            }
        );

        const result = await response.text();

        if (result === "Funds transferred") {

            alert("Success");

            // Get updated account
            const updatedResponse = await fetch(
                `http://localhost:3005/api/login/${account.id}`
            );

            const updatedAccount: Account =
                await updatedResponse.json();

            // Update React state
            setCurrentAccount(updatedAccount);

            setTransferAmount("");
            setTransferTo("");

        } else if(result === "Provide proper id"){
            alert("Please provide a valid to id");
            return; 
        } else if(result === "Insufficient funds"){
            alert("Insufficient funds");
            return; 
        }
        else {
            alert(result);
        }
    
    }

    return (
        <div>

            <button onClick={signOut} style={{ marginRight: "12px" }}>
                Sign Out
            </button>

            <button onClick={deleteAccount}>
                Terminate Account
            </button>

            <h1>Welcome {account.username}</h1>

            <h2>Current Balance: {account.balance}</h2>

            <input
                type="text"
                placeholder="Deposit Amount"
                value={depositAmount}
                onChange={(e) => setDepositAmount(e.target.value)}
            />
            <button onClick={Deposit}>Deposit</button>
            <br />
            <br />
            <input
                type="text"
                placeholder="Withdraw Amount"
                value={withdrawAmount}
                onChange={(e) => setWithdrawAmount(e.target.value)}
            />
            <button onClick={Withdraw}>Withdraw</button>
            <br />
            <br />
            <input
                type="text"
                placeholder="To id"
                value={transferTo}
                onChange={(e) => setTransferTo(e.target.value)}
            />
            <input
                type="text"
                placeholder="Transfer Amount"
                value={transferAmount}
                onChange={(e) => setTransferAmount(e.target.value)}
            />
            <button onClick={Transfer}>Transfer</button>
            <br />
            <br />

            <h2>Transaction History</h2>

            {account.transactionHistory.map((transaction) => (
                <div key={transaction.timestamp}>
                    <p>
                        {/* For WITHDRAWAL and DEPOSIT - No need for From and To */}
                        {!(transaction.transactionType === "WITHDRAW" || transaction.transactionType === "DEPOSIT") && (
                            <>
                                From: {transaction.from}
                                <br />
                                To: {transaction.to}
                            </>
                        )}
                        <br />
                        Amount: {transaction.amount}
                        <br />
                        Original Balance: {transaction.originalBalance}
                        <br />
                        New Balance: {transaction.newBalance}
                        <br />
                        Type: {transaction.transactionType}
                        <br />
                        Timestamp: {transaction.timestamp}
                    </p>
                    <br/>
                </div>
            ))}

        </div>
    );
}

export default UserPage;