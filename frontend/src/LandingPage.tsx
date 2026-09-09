import { useEffect, useState } from "react";
import type { Account } from "./types";

type LandingPageProps = {
    setCurrentAccount: React.Dispatch<
        React.SetStateAction<Account | null>
    >;
};

function LandingPage({ setCurrentAccount }: LandingPageProps) {

    const [username, setUsername] = useState("");
    const [loginId, setLoginId] = useState("");
    const [accounts, setAccounts] = useState<Account[]>([]);

    // to print all account details 
    async function getAccounts() {

        const response = await fetch(
            "http://localhost:3005/api/accounts"
        );

        const data: Account[] = await response.json();

        setAccounts(data);
    }

    async function createAcc() {

        if(username == ""){
            alert("Invalid username");
            return; 
        }

        const response = await fetch(
            "http://localhost:3005/api/accounts",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username
                })
            }
        );

        const account: Account = await response.json();

        alert("Account created! Your ID is " + account.id);

        setUsername("");

        // Refresh account list
        getAccounts();
    }

    async function login() {

        if(loginId == ""){
            alert("Invalid id");
            return;
        }

        const response = await fetch(
            `http://localhost:3005/api/login/${loginId}`
        );

        if (!response.ok) {
            alert("Invalid user ID");
            return;
        }

        const account: Account = await response.json();

        // This will trigger App.tsx to move to UserPage
        setCurrentAccount(account);
    }

    useEffect(() => {
        getAccounts();
    }, []);

    return (
        <div>

            <h1>Fast & Reckless Bank</h1>

            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />

            <button onClick={createAcc}>
                Create Account
            </button>

            <br />
            <br />

            <input
                type="number"
                placeholder="User ID"
                value={loginId}
                onChange={(e) => setLoginId(e.target.value)}
            />

            <button onClick={login}>
                Login
            </button>

            <br /><br />
            <h2>Existing Accounts</h2>

            {accounts.map((account) => (
                <div key={account.id}>
                    <p>
                        User ID: {account.id}
                        <br />
                        Username: {account.username}
                    </p>
                    <br />
                </div>
            ))}

        </div>
    );
}

export default LandingPage;