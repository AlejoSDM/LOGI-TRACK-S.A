
const API = 'http://localhost:8080';

let S = JSON.parse(localStorage.getItem('lt') || 'null');

const $ = s => document.querySelector(s);
const main = $('#main');

const esc = v => String(v ?? '').replace(/[&<>"]/g, c => ({
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;'
}[c]));

const now = () => {
    const d = new Date();
    return new Date(d - d.getTimezoneOffset() * 60000)
        .toISOString()
        .slice(0, 19);
};

let toastTimer;

function toast(message, bad = false) {
    const t = $('#toast');

    t.textContent = message;
    t.className = bad ? 'bad show' : 'show';

    clearTimeout(toastTimer);

    toastTimer = setTimeout(() => {
        t.className = '';
    }, 3500);
}

async function api(path, method = 'GET', body) {
    const response = await fetch(API + path, {
        method,
        headers: {
            'Content-Type': 'application/json',
            ...(S?.token && {
                Authorization: 'Bearer ' + S.token
            })
        },
        body: body !== undefined ? JSON.stringify(body) : undefined
    });

    const data = await response.json().catch(() => null);

    if (response.status === 401 && S) {
        logout();
        throw new Error('Sesión expirada. Ingresa nuevamente.');
    }

    if (response.status === 403) {
        throw new Error('No tienes permisos para esta acción.');
    }

    if (!response.ok) {
        const validation = data?.errors
            ? Object.values(data.errors).join(', ')
            : null;

        throw new Error(
            validation ||
            data?.message ||
            'Error en la solicitud: ' + response.status
        );
    }

    return data;
}

const screen = name => {
    ['auth', 'pending', 'app'].forEach(section => {
        $('#' + section).hidden = section !== name;
    });
};

function logout() {
    S = null;
    localStorage.removeItem('lt');

    $('#nav').innerHTML = '';
    main.innerHTML = '';

    screen('auth');
}

// =====================================================
// AUTENTICACIÓN
// =====================================================

$('.tabs').onclick = event => {
    const target = event.target.closest('button[data-t]');

    if (!target) return;

    const formName = target.dataset.t;

    document.querySelectorAll('.tabs button').forEach(button => {
        button.classList.toggle('on', button === target);
    });

    ['fLogin', 'fReg'].forEach(id => {
        $('#' + id).hidden = id !== formName;
    });
};

$('#fLogin').onsubmit = async event => {
    event.preventDefault();

    const form = event.target;
    const button = form.querySelector('button[type="submit"]');

    button.disabled = true;
    button.textContent = 'Ingresando...';

    try {
        const data = await api(
            '/auth/login',
            'POST',
            Object.fromEntries(new FormData(form))
        );

        if (!data.estado) {
            screen('pending');
            return;
        }

        S = data;

        localStorage.setItem('lt', JSON.stringify(data));

        form.reset();
        start();

    } catch (error) {
        toast(error.message, true);

    } finally {
        button.disabled = false;
        button.innerHTML = 'Ingresar <span>→</span>';
    }
};

$('#fReg').onsubmit = async event => {
    event.preventDefault();

    const form = event.target;
    const button = form.querySelector('button[type="submit"]');

    button.disabled = true;
    button.textContent = 'Enviando solicitud...';

    try {
        await api(
            '/auth/register',
            'POST',
            Object.fromEntries(new FormData(form))
        );

        form.reset();
        screen('pending');

    } catch (error) {
        toast(error.message, true);

    } finally {
        button.disabled = false;
        button.innerHTML = 'Solicitar acceso <span>→</span>';
    }
};

$('#back').onclick = () => screen('auth');
$('#out').onclick = logout;

// =====================================================
// UTILIDADES
// =====================================================

const tbl = (headers, rows) => `
    <table>
        <thead>
            <tr>
                ${headers.map(header => `<th>${header}</th>`).join('')}
            </tr>
        </thead>

        <tbody>
            ${
                rows.length
                    ? rows.map(row => `
                        <tr>
                            ${row.map(cell => `<td>${cell}</td>`).join('')}
                        </tr>
                    `).join('')
                    : `
                        <tr>
                            <td colspan="${headers.length}" class="muted">
                                No hay registros para mostrar.
                            </td>
                        </tr>
                    `
            }
        </tbody>
    </table>
`;

const SRC = {
    ciudades: [
        '/api/ciudades',
        'idCiudad',
        'nombreCiudad'
    ],

    marcas: [
        '/api/marcas',
        'id',
        'nombreMarca'
    ],

    categorias: [
        '/api/categorias',
        'id',
        'tipoCategoria'
    ],

    productos: [
        '/api/productos',
        'id',
        'nombre'
    ],

    bodegas: [
        '/api/bodegas',
        'id',
        'nombre'
    ],

    empleados: [
        '/api/usuarios/rol/EMPLEADO/estado/true',
        'id',
        'username'
    ]
};

const L = {};

async function lk(...names) {
    for (const name of names) {
        const [path, idField, labelField] = SRC[name];

        const records = await api(path);

        L[name] = records.map(record => ({
            v: record[idField],
            l: record[labelField]
        }));
    }
}

const name = (source, value) => {
    return L[source]?.find(item => item.v == value)?.l ?? value;
};

const opts = (items, current) => {
    return items.map(item => `
        <option
            value="${esc(item.v)}"
            ${item.v == current ? 'selected' : ''}
        >
            ${esc(item.l)}
        </option>
    `).join('');
};

const inp = ([field, label, type], current = '') => {
    if (Array.isArray(type)) {
        return `
            <label>
                ${label}
                <select name="${field}" required>
                    <option value="">Seleccione...</option>
                    ${opts(type.map(value => ({
                        v: value,
                        l: value
                    })), current)}
                </select>
            </label>
        `;
    }

    if (SRC[type]) {
        return `
            <label>
                ${label}
                <select name="${field}" required>
                    <option value="">Seleccione...</option>
                    ${opts(L[type] || [], current)}
                </select>
            </label>
        `;
    }

    return `
        <label>
            ${label}
            <input
                name="${field}"
                type="${type === 'number' ? 'number' : 'text'}"
                ${type === 'number' ? 'step="any"' : ''}
                value="${esc(current)}"
                required
            >
        </label>
    `;
};

// =====================================================
// CRUD GENERAL - ADMINISTRADOR
// =====================================================

const E = {
    ciudades: {
        t: 'Ciudades',
        p: '/api/ciudades',
        id: 'idCiudad',
        f: [
            ['nombreCiudad', 'Nombre']
        ]
    },

    marcas: {
        t: 'Marcas',
        p: '/api/marcas',
        f: [
            ['nombreMarca', 'Nombre']
        ]
    },

    categorias: {
        t: 'Categorías',
        p: '/api/categorias',
        f: [
            [
                'tipoCategoria',
                'Tipo de categoría',
                ['COMIDA', 'TECNOLOGIA', 'ROPA', 'SALUD']
            ]
        ]
    },

    productos: {
        t: 'Productos',
        p: '/api/productos',
        f: [
            ['nombre', 'Nombre'],
            ['marcaId', 'Marca', 'marcas'],
            ['categoriaId', 'Categoría', 'categorias'],
            ['precioUnitario', 'Precio unitario', 'number']
        ]
    },

    bodegas: {
        t: 'Bodegas',
        p: '/api/bodegas',
        m: {
            empleadoId: 'usuarioId'
        },
        f: [
            ['nombre', 'Nombre'],
            ['ciudadId', 'Ciudad', 'ciudades'],
            ['capacidad', 'Capacidad', 'number'],
            ['empleadoId', 'Encargado', 'empleados']
        ]
    },

    inventarios: {
        t: 'Inventario por bodega',
        p: '/api/inventarios',
        f: [
            ['bodegaId', 'Bodega', 'bodegas'],
            ['productoId', 'Producto', 'productos'],
            ['stock', 'Stock', 'number']
        ]
    }
};

async function crud(key) {
    const entity = E[key];

    const sources = [
        ...new Set(
            entity.f
                .map(field => field[2])
                .filter(source => SRC[source])
        )
    ];

    await lk(...sources);

    const rows = await api(entity.p);

    const idField = entity.id || 'id';

    let editingId = null;

    main.innerHTML = `
        <h2>${entity.t}</h2>

        <form id="f" class="card grid">
            ${entity.f.map(field => inp(field)).join('')}

            <button type="submit" id="saveBtn">
                Guardar registro
            </button>

            <button
                type="button"
                id="cx"
                class="sec"
                hidden
            >
                Cancelar edición
            </button>
        </form>

        ${tbl(
            [
                ...entity.f.map(field => field[1]),
                'Acciones'
            ],

            rows.map(record => [
                ...entity.f.map(([field, , type]) => {
                    return esc(
                        SRC[type]
                            ? name(type, record[entity.m?.[field] || field])
                            : record[field]
                    );
                }),

                `
                    <button
                        type="button"
                        data-ed="${esc(record[idField])}"
                    >
                        Editar
                    </button>

                    <button
                        type="button"
                        class="del"
                        data-del="${esc(record[idField])}"
                    >
                        Eliminar
                    </button>
                `
            ])
        )}
    `;

    const form = $('#f');
    const cancel = $('#cx');

    form.onsubmit = async event => {
        event.preventDefault();

        const button = $('#saveBtn');
        button.disabled = true;

        try {
            const body = Object.fromEntries(
                new FormData(form)
            );

            entity.f.forEach(([field, , type]) => {
                if (type === 'number' || SRC[type]) {
                    body[field] = Number(body[field]);
                }
            });

            await api(
                entity.p + (editingId ? '/' + editingId : ''),
                editingId ? 'PUT' : 'POST',
                body
            );

            toast(
                editingId
                    ? 'Registro actualizado correctamente.'
                    : 'Registro guardado correctamente.'
            );

            await crud(key);

        } catch (error) {
            toast(error.message, true);

        } finally {
            button.disabled = false;
        }
    };

    cancel.onclick = () => {
        editingId = null;
        form.reset();
        cancel.hidden = true;
        $('#saveBtn').textContent = 'Guardar registro';
    };

    main.onclick = async event => {
        const target = event.target.closest('button');

        if (!target) return;

        if (target.dataset.ed !== undefined) {
            const record = rows.find(
                item => item[idField] == target.dataset.ed
            );

            if (!record) return;

            editingId = record[idField];

            entity.f.forEach(([field]) => {
                const responseField =
                    entity.m?.[field] || field;

                form.elements[field].value =
                    record[responseField] ?? '';
            });

            cancel.hidden = false;
            $('#saveBtn').textContent = 'Actualizar registro';

            form.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }

        if (target.dataset.del !== undefined) {
            const id = target.dataset.del;

            if (!confirm('¿Estás seguro de eliminar este registro?')) {
                return;
            }

            try {
                await api(
                    entity.p + '/' + id,
                    'DELETE'
                );

                toast('Registro eliminado correctamente.');

                await crud(key);

            } catch (error) {
                toast(error.message, true);
            }
        }
    };
}

// =====================================================
// SOLICITUDES DE REGISTRO - ADMINISTRADOR
// =====================================================

async function solicitudes() {
    const records = await api('/api/solicitudes');

    main.innerHTML = `
        <h2>Solicitudes de registro</h2>

        <div class="card">
            <h3>Usuarios pendientes de aprobación</h3>
            <p class="muted">
                Revisa las solicitudes y autoriza el acceso
                de los nuevos empleados.
            </p>
        </div>

        ${
            records.length
                ? tbl(
                    ['Nombre completo', 'Usuario', 'Acciones'],

                    records.map(user => [
                        esc(user.nombre),
                        esc(user.username),

                        `
                            <button
                                type="button"
                                data-ok="${esc(user.id)}"
                            >
                                Aceptar
                            </button>

                            <button
                                type="button"
                                class="del"
                                data-no="${esc(user.id)}"
                            >
                                Rechazar
                            </button>
                        `
                    ])
                )
                : `
                    <p class="muted">
                        No hay solicitudes pendientes.
                    </p>
                `
        }
    `;

    main.onclick = async event => {
        const target = event.target.closest('button');

        if (!target) return;

        const id = target.dataset.ok || target.dataset.no;

        if (!id) return;

        const isApproval = target.dataset.ok !== undefined;

        if (
            !isApproval &&
            !confirm('¿Deseas rechazar esta solicitud?')
        ) {
            return;
        }

        try {
            await api(
                '/api/solicitudes/' +
                id +
                (isApproval ? '/aprobar' : ''),
                isApproval ? 'PUT' : 'DELETE'
            );

            toast(
                isApproval
                    ? 'Empleado aprobado correctamente.'
                    : 'Solicitud rechazada.'
            );

            await solicitudes();

        } catch (error) {
            toast(error.message, true);
        }
    };
}

// =====================================================
// RESUMEN - ADMINISTRADOR
// =====================================================

async function resumen() {
    await lk('bodegas', 'productos');

    const totals = await Promise.all(
        L.bodegas.map(async warehouse => {
            const total = await api(
                `/api/inventarios/bodega/${warehouse.v}/total-stock`
            );

            return [
                warehouse.l,
                total
            ];
        })
    );

    const lowStock = await api(
        '/api/inventarios/stock-bajo?limite=10'
    );

    main.innerHTML = `
        <h2>Resumen general</h2>

        <div class="card">
            <h3>Stock total por bodega</h3>
            <p class="muted">
                Consulta las unidades registradas en cada bodega.
            </p>
        </div>

        ${tbl(
            ['Bodega', 'Stock total'],

            totals.map(([warehouse, total]) => [
                esc(warehouse),
                esc(
                    typeof total === 'object'
                        ? JSON.stringify(total)
                        : total
                )
            ])
        )}

        <h3>Productos con stock bajo</h3>

        <p class="muted">
            Productos que requieren revisión por su nivel de existencias.
        </p>

        ${tbl(
            ['Bodega', 'Producto', 'Stock'],

            lowStock.map(item => [
                esc(name('bodegas', item.bodegaId)),
                esc(name('productos', item.productoId)),
                `<b class="low">${esc(item.stock)}</b>`
            ])
        )}
    `;
}

// =====================================================
// AUDITORÍAS E HISTORIAL
// =====================================================

async function auditorias() {
    const isAdmin = S.rol === 'ADMIN';

    await lk(
        'bodegas',
        'productos',
        ...(isAdmin ? ['empleados'] : [])
    );

    main.innerHTML = `
        <h2>${isAdmin ? 'Auditorías' : 'Mi historial'}</h2>

        <form id="f" class="card grid">
            <label>
                Tipo de movimiento
                <select name="tipo">
                    <option value="">Todos</option>
                    <option value="ENTRADA">Entrada</option>
                    <option value="SALIDA">Salida</option>
                    <option value="TRANSFERENCIA">Transferencia</option>
                </select>
            </label>

            ${
                isAdmin
                    ? `
                        <label>
                            Usuario
                            <select name="u">
                                <option value="">Todos</option>
                                ${opts(L.empleados)}
                            </select>
                        </label>
                    `
                    : ''
            }

            <label>
                Desde
                <input type="datetime-local" name="d">
            </label>

            <label>
                Hasta
                <input type="datetime-local" name="h">
            </label>

            <button type="submit">
                Aplicar filtros
            </button>
        </form>

        <div id="auditResults"></div>
    `;

    const run = async () => {
        const query = Object.fromEntries(
            new FormData($('#f'))
        );

        const userId = isAdmin
            ? query.u
            : S.usuarioId;

        const datePath = query.d && query.h
            ? `/fecha?desde=${encodeURIComponent(query.d)}&hasta=${encodeURIComponent(query.h)}`
            : '';

        const [entries, transfers] = await Promise.all([
            api('/api/auditorias-salida-entrada' + datePath),
            api('/api/auditorias-transferencias' + datePath)
        ]);

        const records = [
            ...entries.map(item => ({
                ...item,
                tipo: item.tipoAccion
            })),

            ...transfers.map(item => ({
                ...item,
                tipo: 'TRANSFERENCIA'
            }))
        ]
            .filter(item => {
                const matchesUser =
                    !userId || item.usuarioId == userId;

                const matchesType =
                    !query.tipo || item.tipo === query.tipo;

                return matchesUser && matchesType;
            })
            .sort((a, b) => {
                return String(b.fecha).localeCompare(
                    String(a.fecha)
                );
            });

        $('#auditResults').innerHTML = tbl(
            [
                'Fecha',
                'Tipo',
                'Usuario',
                'Bodega',
                'Producto',
                'Cantidad'
            ],

            records.map(item => {
                const warehouse = item.bodegaId
                    ? name('bodegas', item.bodegaId)
                    : (
                        name('bodegas', item.bodegaOrigenId) +
                        ' → ' +
                        name('bodegas', item.bodegaDestinoId)
                    );

                return [
                    esc(
                        String(item.fecha)
                            .replace('T', ' ')
                            .slice(0, 16)
                    ),

                    esc(item.tipo),

                    esc(name('empleados', item.usuarioId)),

                    esc(warehouse),

                    esc(name('productos', item.productoId)),

                    esc(item.cantidad)
                ];
            })
        );
    };

    $('#f').onsubmit = event => {
        event.preventDefault();

        run().catch(error => {
            toast(error.message, true);
        });
    };

    await run();
}


// =====================================================
// REPORTES GENERALES DE AUDITORÍA - ADMINISTRADOR
// =====================================================

async function reportesGenerales() {
    // Consultar todos los reportes registrados
    const records = await api('/api/reportes-auditorias');

    main.innerHTML = `
        <h2>Reportes generales de auditoría</h2>

        <p class="muted">
            Consulta las acciones realizadas por los usuarios
            dentro del sistema.
        </p>

        <form id="fReportes" class="card grid">

            <label>
                Módulo
                <select name="modulo">
                    <option value="">Todos los módulos</option>
                    <option value="PRODUCTO">Producto</option>
                    <option value="MARCA">Marca</option>
                    <option value="CATEGORIA">Categoría</option>
                    <option value="BODEGA">Bodega</option>
                    <option value="CIUDAD">Ciudad</option>
                    <option value="INVENTARIO">Inventario</option>
                </select>
            </label>

            <label>
                Tipo de acción
                <select name="accion">
                    <option value="">Todas las acciones</option>
                    <option value="CREAR">Crear</option>
                    <option value="MODIFICAR">Modificar</option>
                    <option value="ELIMINAR">Eliminar</option>
                    <option value="ENTRADA">Entrada</option>
                    <option value="SALIDA">Salida</option>
                    <option value="TRANSFERENCIA">Transferencia</option>
                </select>
            </label>

            <label>
                Desde
                <input type="datetime-local" name="desde">
            </label>

            <label>
                Hasta
                <input type="datetime-local" name="hasta">
            </label>

            <label>
                Usuario
                <input
                    type="text"
                    name="usuario"
                    placeholder="Buscar por nombre de usuario"
                >
            </label>

            <button type="submit">
                Aplicar filtros
            </button>

            <button type="reset" class="sec">
                Limpiar filtros
            </button>

        </form>

        <div id="reportesResultados"></div>
    `;

    const form = $('#fReportes');
    const results = $('#reportesResultados');

    const render = () => {
        const filters = Object.fromEntries(
            new FormData(form)
        );

        const filtered = records.filter(item => {
            const matchesModule =
                !filters.modulo ||
                item.modulo === filters.modulo;

            const matchesAction =
                !filters.accion ||
                item.tipoAccion === filters.accion;

            const matchesUser =
                !filters.usuario ||
                String(item.username ?? '')
                    .toLowerCase()
                    .includes(filters.usuario.toLowerCase());

            const matchesFrom =
                !filters.desde ||
                String(item.fecha) >= filters.desde;

            const matchesUntil =
                !filters.hasta ||
                String(item.fecha) <= filters.hasta;

            return matchesModule &&
                matchesAction &&
                matchesUser &&
                matchesFrom &&
                matchesUntil;
        });

        results.innerHTML = `
            <div class="card">
                <b>Registros encontrados:</b>
                ${filtered.length}
            </div>

            ${tbl(
                [
                    'Fecha',
                    'Usuario',
                    'Módulo',
                    'Acción',
                    'ID registro',
                    'Descripción'
                ],

                filtered.map(item => [
                    esc(
                        String(item.fecha ?? '')
                            .replace('T', ' ')
                            .slice(0, 19)
                    ),

                    esc(item.username),

                    esc(item.modulo),

                    esc(item.tipoAccion),

                    esc(item.registroId ?? '-'),

                    esc(item.descripcion)
                ])
            )}
        `;
    };

    form.onsubmit = event => {
        event.preventDefault();
        render();
    };

    form.onreset = () => {
        setTimeout(render, 0);
    };

    render();
}


// =====================================================
// OPERACIONES DEL EMPLEADO
// =====================================================

const sinBodega = `
    <p class="muted">
        Aún no tienes una bodega asignada.
        Espera a que el administrador te asigne una.
    </p>
`;

const mv = (type, warehouse, product, quantity) => {
    return api(
        '/api/auditorias-salida-entrada',
        'POST',
        {
            tipoAccion: type,
            fecha: now(),
            usuarioId: S.usuarioId,
            bodegaId: Number(warehouse),
            productoId: Number(product),
            cantidad: Number(quantity)
        }
    );
};

const asegurar = async (warehouse, product) => {
    const inventory = await api(
        '/api/inventarios/bodega/' + warehouse
    );

    if (
        inventory.some(item => item.productoId == product)
    ) {
        return undefined;
    }

    const created = await api(
        '/api/inventarios',
        'POST',
        {
            bodegaId: Number(warehouse),
            productoId: Number(product),
            stock: 0
        }
    );

    return created.id;
};

const conLimpieza = async (warehouse, product, operation) => {
    const id = await asegurar(warehouse, product);

    try {
        return await operation();

    } catch (error) {
        if (id) {
            await api(
                '/api/inventarios/' + id,
                'DELETE'
            ).catch(() => {});
        }

        throw error;
    }
};

const qty = `
    <label>
        Cantidad
        <input
            name="c"
            type="number"
            min="1"
            step="1"
            placeholder="Ingresa la cantidad"
            required
            autofocus
        >
    </label>
`;

// =====================================================
// VENTANAS MODALES
// =====================================================

function dlg(title, body, callback) {
    const dialog = $('#dlg');

    dialog.innerHTML = `
        <form method="dialog">
            <h3>${title}</h3>

            ${body}

            <div class="row">
                <button
                    value="x"
                    class="sec"
                    formnovalidate
                >
                    Cancelar
                </button>

                <button value="ok">
                    Confirmar
                </button>
            </div>
        </form>
    `;

    dialog.onclose = async () => {
        const result = dialog.returnValue;

        dialog.returnValue = '';

        if (result !== 'ok') return;

        try {
            await callback(
                Object.fromEntries(
                    new FormData(
                        dialog.querySelector('form')
                    )
                )
            );

            toast('Operación realizada correctamente.');

            await miBodega();

        } catch (error) {
            toast(error.message, true);
        }
    };

    dialog.showModal();
}

// =====================================================
// MI BODEGA - EMPLEADO
// =====================================================

async function miBodega() {
    await lk('productos', 'bodegas');

    const warehouses = await api(
        '/api/bodegas/empleado/' + S.usuarioId
    );

    if (!warehouses.length) {
        main.innerHTML = `
            <h2>Mi bodega</h2>
            ${sinBodega}
        `;

        return;
    }

    let html = `
        <h2>Mis bodegas</h2>

        <p class="muted">
            Consulta tus existencias y registra los movimientos
            de productos de tus bodegas.
        </p>
    `;

    for (const warehouse of warehouses) {
        const inventory = await api(
            '/api/inventarios/bodega/' + warehouse.id
        );

        const total = inventory.reduce(
            (sum, item) => sum + Number(item.stock || 0),
            0
        );

        const capacity = Number(warehouse.capacidad) || 0;

        const percentage = capacity > 0
            ? Math.min(
                100,
                Math.round(total * 100 / capacity)
            )
            : 0;

        html += `
            <div class="wh">

                <div class="whh">
                    <h3>🏭 ${esc(warehouse.nombre)}</h3>

                    <button
                        type="button"
                        data-add="${esc(warehouse.id)}"
                    >
                        + Agregar producto
                    </button>
                </div>

                <div class="cap">
                    <i style="width:${percentage}%"></i>
                </div>

                <small class="muted">
                    ${total} / ${capacity} unidades
                    · ${percentage}% de capacidad
                </small>

                <div class="shelf">

                    ${
                        inventory.length
                            ? inventory.map((item, index) => {
                                const attributes = `
                                    data-b="${esc(warehouse.id)}"
                                    data-p="${esc(item.productoId)}"
                                `;

                                const isLow = item.stock < 10;
                                const isEmpty = Number(item.stock) === 0;

                                return `
                                    <div
                                        class="crate ${isLow ? 'lowbox' : ''}"
                                        style="--i:${index}"
                                    >

                                        <b>
                                            ${esc(
                                                name(
                                                    'productos',
                                                    item.productoId
                                                )
                                            )}
                                        </b>

                                        <span class="q">
                                            ${esc(item.stock)}
                                        </span>

                                        <small>
                                            unidades
                                            ${isLow ? ' · Stock bajo' : ''}
                                        </small>

                                        <div class="acts">

                                            <button
                                                type="button"
                                                data-m="ENTRADA"
                                                ${attributes}
                                            >
                                                + Entrada
                                            </button>

                                            <button
                                                type="button"
                                                data-m="SALIDA"
                                                ${attributes}
                                            >
                                                − Salida
                                            </button>

                                            <button
                                                type="button"
                                                data-m="TRANSFERENCIA"
                                                ${attributes}
                                                title="Transferir producto"
                                            >
                                                ⇄
                                            </button>

                                            ${
                                                isEmpty
                                                    ? `
                                                        <button
                                                            type="button"
                                                            class="del"
                                                            data-delete-inventory="${esc(item.id)}"
                                                            title="Eliminar de mi bodega"
                                                        >
                                                            🗑️ Eliminar
                                                        </button>
                                                    `
                                                    : ''
                                            }

                                        </div>

                                    </div>
                                `;
                            }).join('')

                            : `
                                <p class="muted">
                                    Esta bodega todavía no tiene productos.
                                    Usa «Agregar producto» para registrar
                                    la primera entrada.
                                </p>
                            `
                    }

                </div>

            </div>
        `;
    }

    main.innerHTML = html;

    // MANEJADOR DE CLICS DE MI BODEGA
    main.onclick = async event => {
        const target = event.target.closest('button');

        if (!target) return;

        const {
            add,
            m: movement,
            b: warehouse,
            p: product
        } = target.dataset;

        // ELIMINAR REGISTRO DE INVENTARIO
        if (target.dataset.deleteInventory !== undefined) {
            const inventoryId = target.dataset.deleteInventory;

            if (
                !confirm(
                    '¿Estás seguro de eliminar este producto de tu bodega? El producto seguirá existiendo en el catálogo general.'
                )
            ) {
                return;
            }

            target.disabled = true;

            try {
                await api(
                    '/api/inventarios/' + inventoryId,
                    'DELETE'
                );

                toast('Producto eliminado de tu bodega correctamente.');

                await miBodega();

            } catch (error) {
                toast(error.message, true);
                target.disabled = false;
            }

            return;
        }

        // AGREGAR PRODUCTO
        if (add) {
            dlg(
                'Agregar producto a la bodega',

                `
                    <label>
                        Producto
                        <select name="p" required>
                            <option value="">Seleccione...</option>
                            ${opts(L.productos)}
                        </select>
                    </label>

                    ${qty}
                `,

                data => {
                    return conLimpieza(
                        add,
                        data.p,
                        () => mv(
                            'ENTRADA',
                            add,
                            data.p,
                            data.c
                        )
                    );
                }
            );

            return;
        }

        const productName = esc(
            name('productos', product)
        );

        // ENTRADA O SALIDA
        if (
            movement === 'ENTRADA' ||
            movement === 'SALIDA'
        ) {
            dlg(
                movement === 'ENTRADA'
                    ? `Entrada · ${productName}`
                    : `Salida · ${productName}`,

                qty,

                data => mv(
                    movement,
                    warehouse,
                    product,
                    data.c
                )
            );

            return;
        }

        // TRANSFERENCIA
        if (movement === 'TRANSFERENCIA') {
            const destinations = L.bodegas.filter(
                item => item.v != warehouse
            );

            if (!destinations.length) {
                toast(
                    'No hay otra bodega disponible para transferir.',
                    true
                );

                return;
            }

            dlg(
                `Transferir · ${productName}`,

                `
                    <label>
                        Bodega de destino

                        <select name="d" required>
                            <option value="">Seleccione...</option>
                            ${opts(destinations)}
                        </select>
                    </label>

                    ${qty}
                `,

                data => {
                    return conLimpieza(
                        data.d,
                        product,
                        () => api(
                            '/api/auditorias-transferencias',
                            'POST',
                            {
                                usuarioId: S.usuarioId,
                                fecha: now(),
                                bodegaOrigenId: Number(warehouse),
                                bodegaDestinoId: Number(data.d),
                                productoId: Number(product),
                                cantidad: Number(data.c)
                            }
                        )
                    );
                }
            );
        }
    };
}

// =====================================================
// NAVEGACIÓN
// =====================================================

const V = {
    ADMIN: [
        ['📩 Solicitudes', solicitudes],
        ['🏭 Bodegas', () => crud('bodegas')],
        ['📦 Productos', () => crud('productos')],
        ['🏷️ Marcas', () => crud('marcas')],
        ['🗂️ Categorías', () => crud('categorias')],
        ['🌆 Ciudades', () => crud('ciudades')],
        ['🔍 Auditorías', auditorias],
        ['📋 Reportes generales', reportesGenerales],
        ['📊 Resumen', resumen]
    ],

    EMPLEADO: [
        ['🏭 Mi bodega', miBodega],
        ['🕘 Historial', auditorias]
    ]
};

function show(index) {
    const views = V[S?.rol];

    if (!views || !views[index]) {
        toast('No tienes un módulo disponible para acceder.', true);
        return;
    }

    main.onclick = null;

    [...$('#nav').children].forEach((button, position) => {
        button.classList.toggle(
            'on',
            position === index
        );
    });

    const [label, action] = views[index];

    try {
        action().catch(error => {
            toast(error.message, true);
        });

    } catch (error) {
        toast(error.message, true);
    }
}

function start() {
    if (!S?.token || !V[S.rol]) {
        logout();
        return;
    }

    screen('app');

    const displayName = S.nombre || S.username || 'Usuario';

    $('#who').textContent =
        `${displayName} · ${S.rol}`;

    $('#avatar').textContent =
        displayName.charAt(0).toUpperCase();

    $('#nav').innerHTML = V[S.rol]
        .map(([label]) => `
            <button type="button">
                ${label}
            </button>
        `)
        .join('');

    $('#nav').onclick = event => {
        const button = event.target.closest('button');

        if (!button) return;

        const index = [...$('#nav').children].indexOf(button);

        if (index !== -1) {
            show(index);
        }
    };

    show(0);
}

// =====================================================
// INICIO
// =====================================================

if (S?.token && V[S.rol]) {
    start();
} else {
    screen('auth');
}
