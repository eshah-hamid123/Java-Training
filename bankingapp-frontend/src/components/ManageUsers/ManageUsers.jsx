import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FaEdit, FaTrash } from 'react-icons/fa'; 
import './ManageUsers.css';

const ManageUsers = () => {
    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token")
        axios.get('http://localhost:8080/accounts/all-accounts', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
            .then(response => {
                console.log(response.data);
                setAccounts(response.data);
                setLoading(false);
            })
            .catch(err => {
                setError('Failed to fetch accounts' + err);
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    
    const filteredAccounts = accounts.slice(1);
    const handleEdit = (accountId) => {
        console.log("Edit account with ID:", accountId);
        navigate(`/edit-account/${accountId}`);
    };

    const handleDelete = (accountId) => {
        const token = localStorage.getItem("token")
        axios.delete(`http://localhost:8080/accounts/delete-account/${accountId}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
            .then(() => {
                setAccounts(filteredAccounts.filter(account => account.accountId !== accountId));
            })
            .catch(err => {
                setError('Failed to delete account' + err);
            });
    };

    return (
        <div>
            <button onClick={() => navigate('/create-account')}>Create Account</button>
            {filteredAccounts.length === 0 ? (
                <p>No accounts available</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Address</th>
                            <th>Balance</th>
                            <th>Account Number</th>
                            <th>Actions</th> 
                        </tr>
                    </thead>
                    <tbody>
                        {filteredAccounts.map(account => (
                            <tr key={account.id}>
                                <td>{account.id}</td>
                                <td>{account.username}</td>
                                <td>{account.email}</td>
                                <td>{account.address}</td>
                                <td>{account.balance}</td>
                                <td>{account.accountNumber}</td>
                                <td>
                                    <button onClick={() => handleEdit(account.id)}>
                                        <FaEdit />
                                    </button>
                                    <button onClick={() => handleDelete(account.id)}>
                                        <FaTrash />
                                    </button>
                                </td> 
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default ManageUsers;
