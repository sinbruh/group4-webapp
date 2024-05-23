import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table.jsx";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { asyncApiRequest } from "@/tools/request";
import Head from "next/head";
import { Input } from "@/components/ui/input";

export function UserTable() {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const fetchedUsers = await asyncApiRequest("GET", "/api/users");
        setUsers(fetchedUsers);
      } catch (error) {
        console.error("Error fetching users: ", error);
      }
    };

    fetchUsers();
  }, [currentPage]);

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(users.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  const getFilteredAndSortedUsers = (users, searchTerm) => {
    return users
        .filter(
            (user) =>
                user.id.toString().includes(searchTerm) ||
                user.email.includes(searchTerm) ||
                 user.roles.some((role) => role.name.includes(searchTerm))
               )
               .sort((a, b) => a.id - b.id);
  };

  const filteredAndSortedUsers = getFilteredAndSortedUsers(users, searchTerm);

  const usersForCurrentPage = filteredAndSortedUsers.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );


  return (
      <>
        <Head>
          <title>Table over users</title>
        </Head>
        <div
            className={
              "mx-auto p-6  flex flex-col  overflow-auto"
            }
        >
          <div className={"flex-grow overflow-auto"}>
            <h2 className="text-2xl font-semibold mb-4">View Users</h2>
            <Input
            type="String"
            placeholder="Search"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            />
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
                  const roles = user.roles.filter(
                      (role) =>
                          !(
                              role.name === "ROLE_USER" &&
                              user.roles.some((r) => r.name === "ROLE_ADMIN")
                          )
                  );
                  
                  return (
                      <TableRow key={user.id}>
                        <TableCell className="font-medium">{user.id}</TableCell>
                        <TableCell>{user.email}</TableCell>
                        <TableCell>
                          {roles.map((role) => role.name).join(", ")}
                        </TableCell>
                      </TableRow>
                  );
                })}
              </TableBody>
            </Table>
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious onClick={handlePreviousPage}/>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#" isActive>
                    {currentPage}
                  </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext onClick={handleNextPage}/>
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        </div>
      </>
  );
}
