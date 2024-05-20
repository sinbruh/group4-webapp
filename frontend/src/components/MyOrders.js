import { getCookie } from '@/tools/cookies';
import React, { useEffect, useState } from 'react';

export default function MyOrders() {
    const [users, setUsers] = useState([]);

    const updateJsonFile = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/users', {
                headers: {
                    'Authorization': 'Bearer ' + getCookie('jwt'),
                }
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
    
            let data = await response.json();
    
            if (data) {
                setUsers(data);
            }
        } catch (error) {
            console.error('Error updating JSON file:', error);
        }
    };

    useEffect(() => {
        updateJsonFile();
    }, []);

    console.log("MyOrders.js: ", users);





    return (
        <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
        <h2 className={"text-2x1 font-semibold mb-4"}>My Orders</h2>

            <form className={"space-y-2"}>
            
                <label>
                   
                </label>
                
            </form>
        </div>
    );
}