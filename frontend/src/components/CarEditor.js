/*fetch car data object from
const updateJsonFile = async () => {
    try {


        //check data
        const response = await fetch('http://localhost:8080/api/cars');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        let data = await response.json();

        if (data) {


        // Add img property to each car configuration
        data = data.map(item => {
            item.configurations = item.configurations.map(config => {
                config.img = `${item.make.replace(/ /g, '-')}-${item.model.replace(/ /g, '-')}.webp`;
                return config;
            });
            return item;
        });
    }
*/

import { ColumnDef,
    ColumnFiltersState,
    SortingState,
    VisibilityState,
    flexRender,
    getCoreRowModel,
    getFilteredRowModel,
    getPaginationRowModel,
    getSortedRowModel,
    useReactTable } from 'react-table';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Input } from "@/components/ui/input"
  

function DataTable({ columns, data }) {
  const table = useReactTable({
    data,
    columns
  });

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          {table.getHeaderGroups().map((headerGroup) => (
            <TableRow key={headerGroup.id}>
              {headerGroup.headers.map((header) => {
                return (
                  <TableHead key={header.id}>
                    {header.isPlaceholder ? null : header.column.columnDef.header}
                  </TableHead>
                )
              })}
            </TableRow>
          ))}
        </TableHeader>
        <TableBody>
  {table.rows.length ? (
    table.rows.map((row) => (
      <TableRow
        key={row.id}
        data-state={row.getIsSelected() && "selected"}
      >
        {row.cells.map((cell) => (
          <TableCell key={cell.id}>
            {cell.render('Cell')}
          </TableCell>
        ))}
      </TableRow>
    ))
  ) : (
    <TableRow>
      <TableCell colSpan={columns.length} className="h-24 text-center">
        No results.
      </TableCell>
    </TableRow>
  )}
</TableBody>
      </Table>
    </div>
  );
}

export default DataTable;