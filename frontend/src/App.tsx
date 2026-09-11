import { useState, useEffect } from "react";
import LandingPage from "./LandingPage";
import UserPage from "./UserPage";
import type { Account } from "./types";

function App() {

    const [currentAccount, setCurrentAccount] = useState<Account | null>(null);

    useEffect(() => {
        const userId = localStorage.getItem("userId");
        if (userId === null) {
            return;
        }

        fetch(`http://localhost:3005/api/login/${userId}`)
            .then(response => response.json())
            .then(account => {
                setCurrentAccount(account);
            });
    },[])

    // depending on login status - display landing page OR user page 
    if (currentAccount === null) {
        return (
            <LandingPage
                setCurrentAccount={setCurrentAccount}
            />
        );
    } 
    
    return (
      //  Pass 2 props to UserPage - account (logged in account) and setCurrentAccount (for logout) 
      <UserPage
        account={currentAccount}
        setCurrentAccount={setCurrentAccount}
      />
    );
    
}

export default App;