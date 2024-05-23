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

  const receiptsForCurrentPage = userDetails.receipts.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div
      className={
        "max-w-7xl mx-auto p-6 bg-white shadow-md rounded-md flex flex-col h-[95vh] overflow-auto"
      }
    >
      <form className={"space-y-2"}>
        <div className={"flex-grow overflow-auto"}>
          <h2 className={"text-2x1 font-semibold mb-4"}>My Orders</h2>
          <Table>
            <TableCaption>A list of your Orders</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">Order ID</TableHead>
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
                receiptsForCurrentPage.map((receipt, index) => {
                  console.log("MyOrders.js: ", receipt);
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
  );
}
