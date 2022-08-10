<script>
    import { Router, Route, Link } from "svelte-navigator";
    import Card from './Card.svelte'

    export let judul = "judul"
    export let icon = "mesin_dashboard"
    export let withPlus = false
    export let linkto

    
</script>

<Card useShadow width = 380>
    <div style=" height: 230px;display: flex; flex-direction:column; ">
        <div style="display: flex; width: 100%; margin-right: 15px; margin-bottom: 20px">
            <div style="width: 50px; height: 28px; border-radius: 0 5px 5px 0;   background-color:#A71818; margin-right: 7px; display: flex; align-items: center; padding-left: 15px; margin-left: -10px">
                <img src="./icons/{icon}.png" alt="mesin">
            </div>
            <div style="color: white;position: relative;width: 100%; height: 28px; display:flex;align-items:center; border-radius:5px 10px 10px 5px;  background-image:linear-gradient( to right,#A71818,#FFC7C7); margin-right: 10px; padding-left:10px">
                {judul}
                {#if withPlus}
                <Link to="{linkto}"> 
                    <img style="position: absolute;right:-10px; top:0" src="./icons/plus_button.png" alt="tambah" />
                </Link>
                {/if}
            </div>
            
        </div>
        <slot/>
    </div>
</Card>
