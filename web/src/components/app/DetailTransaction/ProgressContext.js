import React, { createContext, useState } from 'react';

export const ProgressContext = createContext();

export const ProgressProvider = ({ children }) => {
    const [progressState, setProgressState] = useState({
        status: "unconfirmed",
        timelinePosition: 0,
    });

    return (
        <ProgressContext.Provider value={{ progressState, setProgressState }}>
            {children}
        </ProgressContext.Provider>
    );
};
