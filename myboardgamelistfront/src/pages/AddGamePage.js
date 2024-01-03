import { Box, Typography } from "@mui/material";
import AddGameForm from "../components/AddGameForm";

export default function AddGamePage() {
    return (
        <Box
        sx={{
            margin: '2%'
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: '#fe2800'
                }}
            >
                Dodaj grę do swojej listy
            </Typography>
            <AddGameForm />
        </Box>
    );
}