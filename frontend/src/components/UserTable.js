import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableFooter,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table.jsx";
import {asyncApiRequest} from "@/tools/request";
import {getCookie} from "@/tools/cookies";
import {isAdmin} from "@/tools/authentication";

export function UserTable() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const fetchedUsers = await asyncApiRequest('GET', '/api/users');
                setUsers(fetchedUsers);
            } catch (error) {
                console.error('Error fetching users: ', error);
            }
        };

        fetchUsers();
    }, []);


    return (
        <Table>
            <TableCaption>A list of all users.</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">User ID</TableHead>
                    <TableHead>Email</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {users.map((user) => (
                    <TableRow key={user.id}>
                        <TableCell className="font-medium">{user.id}</TableCell>
                        <TableCell>{user.email}</TableCell>
                        {console.log(user)}
                    </TableRow>
                    ))}
            </TableBody>
        </Table>
    );
}