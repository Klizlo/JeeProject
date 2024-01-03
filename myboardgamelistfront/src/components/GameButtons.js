import { Alert, Button, Dialog, DialogActions, DialogTitle, IconButton, Snackbar} from "@mui/material";
import { useState } from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import CancelIcon from '@mui/icons-material/Cancel';

export default function GameButtons(params) {

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    const handleClick = () => {
        setOpen(false);
        fetch('http://localhost:8080/api/boardGames/' + params.params.row.id, {
            method: "DELETE",
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + (localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token"))
            },
        }).then((response) => {
            if (response.ok) {
                window.location = '/'
            } else if (response.status === 401) {
                setOpenAlert(true);
                setError("Nieautoryzowane działanie");
            } else {
                return response.json()
            }
        })
        .then((result) => {
            setOpenAlert(true);
            setError("Nie znaleziono gry");
        })
    }

    return (
        <strong>
            <IconButton 
              aria-label="show"
              onClick={() => {
                window.location = '/games/' + params.params.row.id + '/show'
              }}
            >
              <InfoIcon />
            </IconButton>
            <IconButton 
              aria-label="update"
              onClick={() => {
                window.location = '/games/' + params.params.row.id + '/update'
              }}
            >
              <EditIcon />
            </IconButton>
            <IconButton 
              aria-label="delete"
              onClick={() => {
                setOpen(true);
              }}
            >
              <DeleteIcon />
            </IconButton>
            <Dialog
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="draggable-dialog-title"
            >
                <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                Czy chcesz usunąć grę z listy?
                </DialogTitle>
                <DialogActions>
                <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                    Anuluj
                </Button>
                <Button sx={{color: 'red'}} startIcon={<DeleteIcon />} onClick={handleClick}>Usuń</Button>
                </DialogActions>
            </Dialog>
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={openAlert}
                onClose={handleAlertClose}
                autoHideDuration={6000}
            >
                <Alert severity="error" sx={{ width: '100%' }}>
                    {error}
                </Alert>
            </Snackbar>
        </strong>
    )
  }