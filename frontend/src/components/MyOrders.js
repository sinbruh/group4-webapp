import React, { useState } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import Head from "next/head";

export default function MyOrders({ userDetails }) {
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(userDetails.receipts.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  let rentals = userDetails.rentals;
  let receipts = userDetails.receipts;

  let sortedRentals = [...rentals].sort((a, b) => a.id - b.id);
  let sortedReceipts = [...receipts].sort((a, b) => a.id - b.id);

  let filteredAndSortedRentals = sortedRentals.filter(rental => new Date(rental.endDate) > new Date());

  const receiptsForCurrentPage = sortedReceipts.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );
  
  const rentalsForCurrentPage = filteredAndSortedRentals.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
      <>
        <Head>
            <title>My Orders</title>
        </Head>
        <div
            className={
              "max-w-7xl mx-auto p-6  flex flex-col h-[95vh] "
            }
        >
          <form className={"space-y-2"}>
            <div className={"flex-grow overflow-auto"}>
              <h2 className={"text-2x1 font-semibold mb-4"}>My Orders</h2>
              <Table>
                <TableCaption>A list of your Orders</TableCaption>
                <TableHeader>
                  <TableRow>
                    <TableHead>Order ID</TableHead>
                    <TableHead>Date</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                {userDetails &&
                  Array.isArray(userDetails.rentals) &&
                  rentalsForCurrentPage
                      .map((rental, index) => {
                          return (
                            <TableRow key={index}>
                              <TableCell className="font-medium">
                                {rental.id}
                              </TableCell>

                              <TableCell>
                                {"From: " +
                                    rental.startDate +
                                    " To: " +
                                    rental.endDate}
                              </TableCell>
                              <TableCell className="text-right">
                                {rental.totalPrice}
                              </TableCell>
                            </TableRow>
                        );
                      })}
                </TableBody>
              </Table>
            </div>
            <div className={"p-4 bg-white"}>
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
          </form>
          <form className={"space-y-2"}>
            <div className={"flex-grow overflow-auto"}>
              <h2 className={"text-2x1 font-semibold mb-4"}>My Receipts</h2>
              <Table>
                <TableCaption>A list of your Orders</TableCaption>
                <TableHeader>
                  <TableRow>
                    <TableHead className="w-[100px]">Receipt ID</TableHead>
                    <TableHead>Car Name</TableHead>
                    <TableHead>Location</TableHead>
                    <TableHead>Provider</TableHead>
                    <TableHead>Date</TableHead>
                    <TableHead className="text-right">Total</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {userDetails &&
                      Array.isArray(userDetails.receipts) &&
                      receiptsForCurrentPage
                      .map((receipt, index) => {
                        
                        return (
                            <TableRow key={index}>
                              <TableCell className="font-medium">
                                {receipt.id}
                              </TableCell>
                              <TableCell>{receipt.carName}</TableCell>
                              <TableCell>{receipt.location}</TableCell>
                              <TableCell>{receipt.providerName}</TableCell>
                              <TableCell>
                                {"From: " +
                                    receipt.startDate +
                                    " To: " +
                                    receipt.endDate}
                              </TableCell>
                              <TableCell className="text-right">
                                {receipt.totalPrice}
                              </TableCell>
                            </TableRow>
                        );
                      })}
                </TableBody>
              </Table>
            </div>
            <div className={"p-4 bg-white"}>
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
          </form>
          
        </div>
      </>
  );
}
