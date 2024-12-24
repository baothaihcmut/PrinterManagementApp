
import React, { useState, useCallback, useEffect, lazy, Suspense} from "react";
import Nav from '../../layout/Nav.jsx';
const LazyLoading = lazy(() => import('./LazyLoading.jsx'));

const transactionListTest = [
  {id: 1, name: "transaction1", status:"pending" , customerName: "TB", createdAt:"2024-12-4", isChange: false},
  {id: 2, name: "transaction2", status:"process" , customerName: "CC", createdAt:"2024-12-4", isChange: false},
  {id: 3, name: "transaction3", status:"failure", customerName: "KL", createdAt:"2024-12-4", isChange: false},
  {id: 4, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 5, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 6, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 7, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 8, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 9, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
  {id: 10, name: "transaction4", status:"done", customerName: "XC", createdAt:"2024-12-4", isChange: false},
]





const PrinterManagement = () => {
  const [status, setStatus] = useState("pending");
  const [visibleData, setVisibleData] = useState(transactionListTest.slice(0, 5));
  const rowsPerPage = 5;

  const loadMoreData = useCallback(() => {
    setVisibleData(prev => {
      const start = prev.length;
      const end = start + rowsPerPage;
      return [...prev, ...transactionListTest.slice(start, end)];
    });
  }, []);

  const hoverButton = (() => "tw-text-gray-500 tw-bg-white tw-transition-all hover:tw-translate-y-[-4px]")();
  const handleChange = (item) => {
    setVisibleData(visibleData.map(transaction => transaction.id === item.id && !item.isChange ? {... transaction, isChange: !item.isChange}: transaction));
  }

  const handleScroll = (e) => {
    const { scrollTop, clientHeight, scrollHeight } = e.target;

    // Trigger when scrolled near the bottom
    if (scrollTop + clientHeight >= scrollHeight - 50) {
      setTimeout(() => {
        loadMoreData();
      }, 1000)
    }
  };

  return (
    <>
      <Nav />
      <div className="tw-mt-28 tw-ml-52">

        {/* status gd */}
        <div className="tw-mt-36">
          <h2 className="tw-text-center tw-text-customBlue tw-font-semibold tw-mb-6">Transaction Management</h2>
          <div className="tw-flex tw-mx-auto tw-w-[90%] tw-h-20 tw-border tw-rounded-full tw-overflow-hidden tw-text-semibold">
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "pending" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("pending")}
            >Pending</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "process" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("process")}
            >Process</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "failure" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("failure")}
            >Failure</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "done" ? "tw-bg-blue-100 tw-text-customBlue" : `${hoverButton}`}`}
            onClick={() => setStatus("done")}
            >Done</button>
          </div>

        </div>

        {/* list gd */}
        <div 
          className="tw-mt-12 tw-py-5 tw-overflow-y-auto tw-h-52 tw-relative"
          onScroll={handleScroll}>
          <Suspense>
            <LazyLoading visibleData={visibleData}
                          status={status}
                          OnChange={handleChange}
                          />
          </Suspense>
        </div>

      </div>
    </>
  )
};

export default PrinterManagement;