function [c,lut] = thin(a)
  
% Louisa Lam, Seong-Whan Lee, and Ching Y. Wuen, "Thinning Methodologies-A
% Comprehensive Survey," IEEE TrPAMI, vol. 14, no. 9, pp. 869-885, 1992.  The
% algorithm is described at the bottom of the first column and the top of the
% second column on page 879.

lut = [];
if (isempty(a))
    c = zeros(size(a));
    return;
end

G1 = uint8(lutthin1);
G2 = uint8(lutthin2);
G3 = uint8(lutthin3);
G4 = uint8(lutthin4);

% Make a lookup table that will produce
% a lookup table indices.  This is avoid
% doing more work in calling applylut
% multiple times with the same input than
% we really need to.

lutlut = 1:512;
lookup = applylut(a, lutlut);
% Preallocate a uint8 zeros matrix
d = uint8(0);
[m,n] = size(a);
d(m,n) = 0;

% First subiteration
d(:) = G1(lookup) & G2(lookup) & G3(lookup);
c = a & ~d;

% Second subiteration
lookup = applylut(c, lutlut);
d(:) = G1(lookup) & G2(lookup) & G4(lookup);
c = c & ~d;