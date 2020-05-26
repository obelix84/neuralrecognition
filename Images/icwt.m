% Функция обратного континуального вейвлет-преобразования
%
% Входные величины:
% spectr - матрица спектральных коэффициентов, посчитанных с помощью cwt(...);
% limits - полосы спектра, по которым восстанавливается функция.
% Если указывается одно число, icwt берет полосу [1 – limits].
% Если указывается вектор, например, [2 4.5 7], сигнал
% восстанавливается по полосам [2 – 4.5] и [4.5 – 7].
% number - вектор числа итераций внутри полосы.
% Длина этого вектора всегда меньше длины вектора limits на единицу.
% wname - имя вейвлета, по которому были подсчитаны спектральные коэффициенты.
% Однако в отличие от функции cwt допускает только вейвлеты,
% у которых есть скейлинг.
%
% Выходные переменные:
% recon - функция, восстановленной по спектральным коэффициентам.
% Число строк матрицы равно числу полос. Каждая строка суть функция,
% восстановленная по соответствующей полосе.
% lengthoficwt - длина строк матрицы recon. Иначе говоря, представляет собой
% длину восстановленных функций.
%
% Пример использования функции icwt:
% [recon, lengthoficwt] = icwt(spectr, [1 1.1 1.2], [10 12], wname); 
% plot(1: lengthoficwt, recon(1, :), 'r', 1: lengthoficwt, recon(2,:), 'k')
%

function [icwt, lengthoficwt] = icwt (spectr, limits, number, wname);
    switch length(limits) 
    case 1
        scales = linspace(limits/number, limits, number);
        icwt = 0;
        for i = 1: limits
            cd = iswt((spectr(i,:)), wname);
            icwt = icwt + cd / (scales (i)) ^2;
        end;
        lengthoficwt = length(icwt);
        otherwise
            icwt = zeros(length(limits)-1, length(spectr));
            for k = 2: length(limits)
                scales = linspace(limits(k-1), limits(k), number(k-1));
                for i = 1: length(scales)
                    cd = iswt((spectr(i,:)), wname) ./ (scales (i)) ^2;
                    icwt(k-1, 1:length(cd)) = icwt(k-1, 1:length(cd)) + cd;
                end; %of i
            end %of k
            lengthoficwt = length(icwt(k-1, :));
        end
return