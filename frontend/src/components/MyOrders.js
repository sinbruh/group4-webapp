import { getCookie } from '@/tools/cookies';
import { set } from 'date-fns';
import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
  } from "@/components/ui/table"

export default function MyOrders() {
    const [user, setUser] = useState(null);
    
    const Email = getCookie('current_email');

    const updateJsonFile = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/users/' + Email, {
                headers: {
                    'Authorization': 'Bearer ' + getCookie('jwt'),
                }
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
    
            let data = await response.json();
    
            if (data) {
                
                setUser(data);
            }
        } catch (error) {
            console.error('Error updating JSON file:', error);
        }
    };

    useEffect(() => {
        updateJsonFile();
    }, []);

    console.log("MyOrders.js: ", user);
    




    return (
        <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
        <h2 className={"text-2x1 font-semibold mb-4"}>My Orders</h2>

            <form className={"space-y-2"}>
            
            <Table>
  <TableCaption>A list of your Orders</TableCaption>
  <TableHeader>
    <TableRow>
      <TableHead className="w-[100px]">Order ID</TableHead>
      <TableHead>Car Name</TableHead>
      <TableHead>Location</TableHead>
      <TableHead>Provider</TableHead>
      <TableHead className="text-right">Total</TableHead>
    </TableRow>
  </TableHeader>
  <TableBody>
    <TableRow>
      <TableCell className="font-medium">id needed</TableCell>
      <TableCell>name needed</TableCell>
      <TableCell>location needed</TableCell>
      <TableCell>provider needed</TableCell>
      <TableCell className="text-right">$price needed</TableCell>
    </TableRow>
    <TableRow>
      <TableCell className="font-medium">id needed</TableCell>
      <TableCell>name needed</TableCell>
      <TableCell>location needed</TableCell>
      <TableCell>provider needed</TableCell>
      <TableCell className="text-right">$price needed</TableCell>
    </TableRow>
    <TableRow>
      <TableCell className="font-medium">id needed</TableCell>
      <TableCell>name needed</TableCell>
      <TableCell>location needed</TableCell>
      <TableCell>provider needed</TableCell>
      <TableCell className="text-right">$price needed</TableCell>
    </TableRow>
  </TableBody>
</Table>
                
            </form>
        </div>
    );
}