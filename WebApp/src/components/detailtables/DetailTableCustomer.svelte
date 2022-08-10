<script>
     import {apiurl} from '../../helper/apiurl.js'
    import { each } from 'svelte/internal';
    import ButtonSmall from '../ButtonSmall.svelte'
    import Card from '../Card.svelte'
    import DataList from '../DataListForCard.svelte'
    import IMP from '../ImagePlaceHolder.svelte'
    import Table from '../Table.svelte'
    import X from 'jquery'

    export let id
    let data

    let nama, iDPelanggan, alamat, tglRegis, noTelp, totalProses
    X.ajax({
            url:apiurl+"daftarpelanggan",
            type:"GET",
            data: {
                id: id
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                nama = result.data.nama
                iDPelanggan = result.data.id
                alamat = result.data.alamat
                tglRegis = result.data.tgl_dibuat
                noTelp = result.data.no_telp
                totalProses = result.data.jml_diproses || "-"
              
          
                // nama = result.data.nama
                // username = result.data.username
                // bergabung = result.data.tgl_dibuat
                // idt = result.data.id
                // notelp = result.data.no_telp
                // keahlian = result.data.keahlian
                // alamat = result.data.alamat

                console.log(result.data.pelanggan);
                
            }         
        },
    )
    function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }
    let dtblCFG =      {
                searching: false, 
                paging: false,
                info: false,
                "ajax":{
                        "url": apiurl+"mesinmilikpelanggan",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
                        "data":{
                            id_pelanggan:id
                        }
                        
                    }, 
                columns: [
                        { data: 'jenis_mesin' },
                        { data: 'jenis_mesin' },
                        { data: 'nama' },
                        { data: 'tgl_masuk' },
                        { data: 'nama_status' },
                        
         
                    ],
                "columnDefs": [ 
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                    {
                        "targets": 1,
                        "render" : function ( data, type, row ) {
                            return data.length > 14 ? data.substring(0,13)+"..." : data
                        },
                    } ,
                    {
                        "targets": 2,
                        "render" : function ( data, type, row ) {
                            return data?.length > 8 ? data.substring(0,7)+"..." : data
                        },
                        "defaultContent": "-"
                    
                    } ,
                    {
                        "targets": 3,
                        "render" : function ( data, type, row ) {
                            return data.length > 8 ? data.substring(0,7)+"..." : data
                        },
                    } 
                ]
            }
    let thead = ['id',
                            'Jenis Mesin',
                            'Teknisi',
                            'Masuk',
                            'Status'
                        ]

</script>
<div style="display: flex; flex-direction:column; gap:8px">

    
    <Card width=500 useBorder> 
        
        <div style="display: flex; flex-direction: column; gap:16px; ">
           
                <DataList title="Nama" isi="{nama}"/>
                <DataList title="ID Pelanggan" isi="{iDPelanggan}"/>
                <DataList title="Alamat" isi="{alamat}"/>
                <DataList title="tgl. Regis" isi="{tglRegis}"/>
                <DataList title="No. Telp" isi="{noTelp}"/>
                <DataList title="Total Proses" isi="{totalProses}"/>
        </div>
        
    </Card>

 


    <Table width=515 dataTableCFG={dtblCFG} tableHead={thead}/>
   
</div>