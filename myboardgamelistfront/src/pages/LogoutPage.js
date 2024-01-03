import { Cancel, Logout } from "@mui/icons-material";
import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material";
import { useNavigate } from "react-router";

export default function LogoutPage(){

    const navigate = useNavigate();

    const handleCancel = () =>{
        navigate(-1);
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("user");
        window.location = '/';
    };

    return (
        <Dialog
            open>
                <DialogTitle >
                    Czy chcesz się wylogować?
                </DialogTitle>
                <DialogActions>
                    <Button
                        startIcon={<Cancel />}
                        sx={{
                            background: 'orange',
                            color: 'white',
                            fontWeight: '700',
                            '&:hover': {
                                background: 'white',
                                color: 'black'
                            }
                        }}
                        onClick={handleCancel}>
                        Anuluj
                    </Button>
                    <Button
                        startIcon={<Logout />}
                        sx={{
                            background: 'grey',
                            color: 'white',
                            fontWeight: '700',
                            '&:hover': {
                                background: 'white',
                                color: 'black'
                            }
                        }}
                        onClick={handleLogout}>
                        Wyloguj
                    </Button>
                </DialogActions>
        </Dialog>
    );
}