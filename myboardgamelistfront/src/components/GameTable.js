import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Grid} from '@mui/material';
import GameButtons from './GameButtons';

let columns = [
  {field: 'name', headerName: 'Nazwa gry', flex: 2, minWidth: 250},
  {field: 'developer', headerName: 'Twórca', flex: 2, minWidth: 250},
  {field: 'minNumberOfPlayers', headerName: 'Minimalna liczba graczy', flex: 1.5, minWidth: 200},
  {field: 'maxNumberOfPlayers', headerName: 'Minimalna liczba graczy', flex: 1.5, minWidth: 200},
  {field: 'numberOfHours', headerName: 'Liczba rozegranych godzin', flex: 1.5, minWidth: 200},
  {
      field: 'Details',
      headerName: 'Szczegóły',
      sortable: false,
      renderCell: (props) => {return <GameButtons params={props} />},
      minWidth: 150
  }
];

export default function GameTable (data) {

  const games = data.data;

  return (
    <Grid
    marginLeft={"auto"}
    marginRight={"auto"}
    p={2}
    container
    alignSelf={"center"}
    alignItems={"center"}
    bgcolor={'action.hover'}
    width={'100%'}
    height={600}>
      <DataGrid 
            rows={games}
            columns={columns}
            pageSize={20}
            disableSelectionOnClick
            rowsPerPageOptions={[20]} />
    </Grid>
  );
}
