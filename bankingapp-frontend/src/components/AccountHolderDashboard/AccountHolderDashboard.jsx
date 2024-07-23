import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const AccountHolderDashboard = () => {
    const location = useLocation();
    const { account } = location.state || {};
    const [accountInfo, setAccountInfo] = useState(null);

    const handleViewAccountDetails = async () => {
        try {
            
            setAccountInfo(account);
        } catch (error) {
            console.error('Error fetching account details', error);
        }
    };

    return (
        <div>
            <button onClick={handleViewAccountDetails}>View Account Details</button>
            {accountInfo && (
                <div>
                    <p>Username: {accountInfo.username}</p>
                    <p>Email: {accountInfo.email}</p>
                    <p>Address: {accountInfo.address}</p>
                    <p>Balance: {accountInfo.balance}</p>
                    <p>Account Number: {accountInfo.accountNumber}</p>
                </div>
            )}
        </div>
    );
};

export default AccountHolderDashboard;
