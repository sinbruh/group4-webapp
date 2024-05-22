import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table.jsx";
import { asyncApiRequest } from "@/tools/request";

export default function ViewOrders({ rental}) {
    const [orders, setOrders] = useState([]);
    const [fetchError, setFetchError] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const fetchedOrders = await asyncApiRequest('GET', '/api/rentals');
                console.log(fetchedOrders)
                setOrders(fetchedOrders);
                setFetchError(null);
            } catch (error) {
                console.error('Error fetching users:', error);
                setFetchError(error.message);
            }
        };

        fetchOrders();
    }, []);

    return (
        <div className="max-w-7xl mx-auto p-6 bg-white shadow-md rounded-md">
            <h2 className="text-2xl font-semibold mb-4">View Orders</h2>
            {fetchError ? (
                <p className="error-message">Error: {fetchError}</p>
            ) : (
                <Table>
                    <TableCaption>A list of all orders.</TableCaption>
                    <TableHeader>
                        <TableRow>
                            <TableHead className="w-[100px]">Order ID</TableHead>
                            <TableHead>Date</TableHead>
                            <TableHead className="text-right">Total</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {orders.map((order) => (
                            <TableRow key={order.id}>
                                <TableCell className="font-medium">{order.id}</TableCell>
                                <TableCell>{"From: " + order.startDate + " To: " + order.endDate}</TableCell>
                                <TableCell className="text-right">{order.totalPrice}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}
        </div>
    );
}
