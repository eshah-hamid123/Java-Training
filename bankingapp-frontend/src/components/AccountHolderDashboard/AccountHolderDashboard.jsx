import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const AccountHolderDashboard = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { account } = location.state || {};
    const [accountInfo, setAccountInfo] = useState(null);

    const handleViewAccountDetails = async () => {
        try {
            setAccountInfo(account);
        } catch (error) {
            console.error('Error fetching account details', error);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        navigate('/');
    };

    const handleSendMoney = () => {
        navigate('/send-money');
    };

    const handleViewTransactionHistory = () => {
        navigate('/transaction-history');
    };

    return (
        <div>
            <button onClick={handleViewAccountDetails}>View Account Details</button>
            <button onClick={handleViewTransactionHistory}>View Transaction History</button>
            <button onClick={handleLogout}>Logout</button>
            <button onClick={handleSendMoney}>Send Money</button>
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
