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
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { asyncApiRequest } from "@/tools/request";
import Head from "next/head";

export default function ViewOrders({ rental }) {
  const [orders, setOrders] = useState([]);
  const [fetchError, setFetchError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 4;

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const fetchedOrders = await asyncApiRequest("GET", "/api/rentals");
        setOrders(fetchedOrders);
        setFetchError(null);
      } catch (error) {
        console.error("Error fetching orders:", error);
        setFetchError(error.message);
      }
    };

    fetchOrders();
  }, []);

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(orders.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  const ordersForCurrentPage = orders.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
      <>
        <Head>
            <title>Total orders of the website</title>
        </Head>
        <div className="mx-auto p-6  flex flex-col  overflow-auto">
          <div className={"flex-grow overflow-auto"}>
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
                    {ordersForCurrentPage.map((order) => (
                        <TableRow key={order.id}>
                          <TableCell className="font-medium">{order.id}</TableCell>
                          <TableCell>
                            {"From: " + order.startDate + " To: " + order.endDate}
                          </TableCell>
                          <TableCell className="text-right">
                            {order.totalPrice}
                          </TableCell>
                        </TableRow>
                    ))}
                  </TableBody>
                </Table>
            )}
          </div>
          <div className={"p-4 bg-white"}>
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
