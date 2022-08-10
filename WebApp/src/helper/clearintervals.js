import { onMount  } from 'svelte'

export function clearIntv() {
    onMount(()=> {
        
            return () => {
                let id = window.setTimeout(function(){},0)
                while(id--){
                    window.clearTimeout(id)
                }
            }
        
            // return () => console.log("kepanggil ga");

    })
}