<script>
    import Card from '../components/Card.svelte'    
    import X from 'jquery'
    import { onMount } from 'svelte'
    export let body = ""
    export let head = "Sukses"
    export let trigger
    export let status
    
    onMount(()=> {

            X('.notifikasi').delay(600).slideDown(200);
            X('.notifikasi').delay(4000).slideUp(600,function(){
                trigger = " "
            });
        
    })

</script>



<Card style="display: none; position: absolute; right: 16px; top: 16px; background-color: white; width: 250px; " Class="notifikasi" useShadow>
    <div style="display:flex;gap:20px">
        <div style="width: 70px; ">
            {#if status == "sukses"}
                 <svg width="100%" height="100%" viewBox="0 0 935 935" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xml:space="preserve" xmlns:serif="http://www.serif.com/" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linecap:round;stroke-linejoin:round;stroke-miterlimit:1.5;">
                <g transform="matrix(1,0,0,1,-1707.49,-4481.12)">
                    <path d="M2642.16,4714.78C2642.16,4585.82 2537.46,4481.11 2408.49,4481.11L1941.16,4481.11C1812.19,4481.11 1707.49,4585.82 1707.49,4714.78L1707.49,5182.12C1707.49,5311.08 1812.19,5415.79 1941.16,5415.79L2408.49,5415.79C2537.46,5415.79 2642.16,5311.08 2642.16,5182.12L2642.16,4714.78Z" style="fill:rgb(69,234,0);"/>
                    <g transform="matrix(1,0,0,1,-77.9595,-96.1237)">
                        <path d="M1935.85,5086.1L2119.45,5269.7L2569.71,4819.45" style="fill:none;stroke:white;stroke-width:120.83px;"/>
                    </g>
                </g>
            </svg>
            {:else}
            <svg width="100%" height="100%" viewBox="0 0 935 935" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xml:space="preserve" xmlns:serif="http://www.serif.com/" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linecap:round;stroke-linejoin:round;stroke-miterlimit:1.5;">
                <g transform="matrix(1,0,0,1,-1707.49,-5805.33)">
                    <g transform="matrix(1,0,0,1,0,1324.21)">
                        <path d="M2642.16,4714.78C2642.16,4585.82 2537.46,4481.11 2408.49,4481.11L1941.16,4481.11C1812.19,4481.11 1707.49,4585.82 1707.49,4714.78L1707.49,5182.12C1707.49,5311.08 1812.19,5415.79 1941.16,5415.79L2408.49,5415.79C2537.46,5415.79 2642.16,5311.08 2642.16,5182.12L2642.16,4714.78Z" style="fill:rgb(193,4,24);"/>
                    </g>
                    <g transform="matrix(1.1203,-0.120301,-0.120301,1.1203,155.052,903.278)">
                        <path d="M2119.45,5269.7L2569.71,4819.45" style="fill:none;stroke:white;stroke-width:120.83px;"/>
                    </g>
                    <g transform="matrix(-1.1203,-0.120301,0.120301,1.1203,4194.6,903.278)">
                        <path d="M2119.45,5269.7L2569.71,4819.45" style="fill:none;stroke:white;stroke-width:120.83px;"/>
                    </g>
                </g>
            </svg>            
            {/if}
           
            
        </div>
        <div>
            <h2 style="font-weight:bold; ">{head}</h2>
            <p class="status-notifikasi">{body}</p>
        </div>
    </div>
    
</Card>