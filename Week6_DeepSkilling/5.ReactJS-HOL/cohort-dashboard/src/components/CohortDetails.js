import React from 'react';
import styles from './CohortDetails.module.css';

const CohortDetails = ({ cohort }) => {
  // Determine heading color based on status
  const getHeadingStyle = (status) => {
    return {
      color: status === 'ongoing' ? 'green' : 'blue'
    };
  };

  return (
    <div className={styles.box}>
      <h3 style={getHeadingStyle(cohort.status)}>
        {cohort.name}
      </h3>
      <dl>
        <dt>Cohort ID:</dt>
        <dd>{cohort.id}</dd>
        
        <dt>Technology:</dt>
        <dd>{cohort.technology}</dd>
        
        <dt>Start Date:</dt>
        <dd>{cohort.startDate}</dd>
        
        <dt>End Date:</dt>
        <dd>{cohort.endDate}</dd>
        
        <dt>Status:</dt>
        <dd>{cohort.status}</dd>
        
        <dt>Participants:</dt>
        <dd>{cohort.participants}</dd>
      </dl>
    </div>
  );
};

export default CohortDetails;