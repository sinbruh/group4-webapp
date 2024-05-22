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
import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination";
import {asyncApiRequest} from "@/tools/request";

export function UserTable() {
    const [users, setUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const entriesPerPage = 2;

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
    }, [currentPage]);

    const handlePreviousPage = () => {
        setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
    }

    const handleNextPage = () => {
        if (currentPage < Math.ceil(users.length / entriesPerPage)) {
            setCurrentPage((prevPage) => prevPage + 1);
        }
    }

    const usersForCurrentPage = users.slice((currentPage - 1) * entriesPerPage, currentPage * entriesPerPage);

    return (
        <div>
            <Table>
                <TableCaption>A list of all users.</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead className="w-[100px]">User ID</TableHead>
                        <TableHead>Email</TableHead>
                        <TableHead>Roles</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {usersForCurrentPage.map((user) => {
                        const roles = user.roles.filter(role => !(role.name === 'ROLE_USER' && user.roles.some(r => r.name === 'ROLE_ADMIN')));
                        console.log('roles', roles);
                        return (
                            <TableRow key={user.id}>
                                <TableCell className="font-medium">{user.id}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>{roles.map(role => role.name).join(', ')}</TableCell>
                            </TableRow>
                        );
                    })}
                </TableBody>
            </Table>
            <Pagination>
                <PaginationContent>
                    <PaginationItem>
                        <PaginationPrevious onClick={handlePreviousPage} />
                    </PaginationItem>
                    <PaginationItem>
                        <PaginationLink href="#" isActive>
                            {currentPage}
                        </PaginationLink>
                    </PaginationItem>
                    <PaginationItem>
                        <PaginationNext onClick={handleNextPage} />
                    </PaginationItem>
                </PaginationContent>
            </Pagination>
        </div>
    );
}