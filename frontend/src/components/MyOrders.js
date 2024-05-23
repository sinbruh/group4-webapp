import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

export default function MyOrders({userDetails}) {


  console.log("MyOrders.js: ", userDetails);

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
              <TableHead>Date</TableHead>
              <TableHead className="text-right">Total</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {userDetails &&
              Array.isArray(userDetails.receipts) &&
              userDetails.receipts.map((receipt, index) => {
                console.log("MyOrders.js: ", receipt); // Add this line
                return (
                  <TableRow key={index}>
                    <TableCell className="font-medium">{receipt.id}</TableCell>
                    <TableCell>{receipt.carName}</TableCell>
                    <TableCell>{receipt.location}</TableCell>
                    <TableCell>{receipt.providerName}</TableCell>
                    <TableCell>
                      {"From: " + receipt.startDate + " To: " + receipt.endDate}
                    </TableCell>
                    <TableCell className="text-right">
                      {receipt.totalPrice}
                    </TableCell>
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </form>
    </div>
  );
}
